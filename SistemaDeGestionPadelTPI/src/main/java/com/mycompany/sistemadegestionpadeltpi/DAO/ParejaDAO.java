package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParejaDAO {

    private Connection conexion;

    public ParejaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // metodo para cargar la pareja a la bbdd
    public boolean insertarParejaYAsignarleGrupo(Pareja pareja, GrupoDAO grupoDAO) throws SQLException {
        int idTorneo = pareja.getIdTorneo();

        List<String> idsGrupo = grupoDAO.obtenerIdsGrupoPorTorneo(idTorneo); // solo grupos de ese torneo

        for (String idGrupo : idsGrupo) {
            // cuantas parejas tiene este grupo y torneo
            String sql = "SELECT COUNT(*) AS cantidad FROM pareja WHERE idGrupo = ? AND idTorneo = ?";
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, idGrupo);
                ps.setInt(2, idTorneo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int cantidad = rs.getInt("cantidad");
                        if (cantidad < 3) {
                            pareja.setIdGrupo(idGrupo);
                            pareja.setIdTorneo(idTorneo);

                            // insertar pareja con idtorneo y idgrupo
                            String insertSql = "INSERT INTO pareja (idJugador1, idJugador2, idTorneo, idGrupo) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement insertPs = conexion.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                                insertPs.setInt(1, pareja.getJugador1().getId());
                                insertPs.setInt(2, pareja.getJugador2().getId());
                                insertPs.setInt(3, idTorneo);
                                insertPs.setString(4, idGrupo);
                                insertPs.executeUpdate();

                                try (ResultSet generatedKeys = insertPs.getGeneratedKeys()) {
                                    if (generatedKeys.next()) {
                                        int idGenerado = generatedKeys.getInt(1);
                                        pareja.setIdPareja(idGenerado);

                                        EstadisticaDAO estadisticaDAO = new EstadisticaDAO(conexion);
                                        estadisticaDAO.insertarEstadisticaInicial(idGenerado);

                                        return true;
                                    } else {
                                        throw new SQLException("No se pudo obtener el ID generado para la pareja.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // buscamos parejas por id
    public Pareja buscarParejaPorId(int idPareja) throws SQLException {
        String sql = "SELECT * FROM pareja WHERE idPareja = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPareja);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    JugadorDAO jugadorDAO = new JugadorDAO(conexion);
                    Jugador j1 = jugadorDAO.buscarJugadorPorId(rs.getInt("idJugador1"));
                    Jugador j2 = jugadorDAO.buscarJugadorPorId(rs.getInt("idJugador2"));
                    String idGrupo = rs.getString("idGrupo");
                    int idTorneo = rs.getInt("idTorneo");
                    return new Pareja(idPareja, j1, j2, idTorneo, idGrupo);
                }
            }
        }
        return null;
    }

    // lista de parejas
    public List<Pareja> obtenerTodasLasParejas() {
        List<Pareja> lista = new ArrayList<>();
        String sql = "SELECT * FROM pareja";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            JugadorDAO jugadorDAO = new JugadorDAO(conexion);

            while (rs.next()) {
                int idPareja = rs.getInt("idPareja");
                int idJugador1 = rs.getInt("idJugador1");
                int idJugador2 = rs.getInt("idJugador2");
                String idGrupo = rs.getString("idGrupo");
                int idTorneo = rs.getInt("idTorneo");
                Jugador jugador1 = jugadorDAO.buscarJugadorPorId(idJugador1);
                Jugador jugador2 = jugadorDAO.buscarJugadorPorId(idJugador2);

                lista.add(new Pareja(idPareja, jugador1, jugador2, idTorneo, idGrupo));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener parejas: " + e.getMessage());
        }

        return lista;
    }

    // metodo auxiliar
    public Pareja buscarParejaPorJugadorId(int idJugador) {
        List<Pareja> lista = obtenerTodasLasParejas();
        for (Pareja p : lista) {
            if (p.getJugador1().getId() == idJugador || p.getJugador2().getId() == idJugador) {
                return p;
            }
        }
        return null;
    }

}
