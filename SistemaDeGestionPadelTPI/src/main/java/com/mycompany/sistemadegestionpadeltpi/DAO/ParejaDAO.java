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
    List<String> idsGrupo = grupoDAO.obtenerTodosLosIdGrupo();

    for (String idGrupo : idsGrupo) {
        // Contar cuántas parejas tiene este grupo
        String sql = "SELECT COUNT(*) AS cantidad FROM pareja WHERE idGrupo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int cantidad = rs.getInt("cantidad");
                    if (cantidad < 3) {
                        // Asignar el grupo al objeto pareja
                        pareja.setIdGrupo(idGrupo);

                        // Insertar pareja
                        String insertSql = "INSERT INTO pareja (idJugador1, idJugador2, idGrupo) VALUES (?, ?, ?)";
                        try (PreparedStatement insertPs = conexion.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                            insertPs.setInt(1, pareja.getJugador1().getId());
                            insertPs.setInt(2, pareja.getJugador2().getId());
                            insertPs.setString(3, idGrupo);
                            insertPs.executeUpdate();

                            // Obtener el ID generado
                            try (ResultSet generatedKeys = insertPs.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int idGenerado = generatedKeys.getInt(1);
                                    pareja.setIdPareja(idGenerado);

                                    // Insertar estadística
                                    EstadisticaDAO estadisticaDAO = new EstadisticaDAO(conexion);
                                    estadisticaDAO.insertarEstadisticaInicial(idGenerado);

                                    return true; // éxito total
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

    return false; // No hay grupos disponibles
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
                    return new Pareja(idPareja, j1, j2, idGrupo);
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

                Jugador jugador1 = jugadorDAO.buscarJugadorPorId(idJugador1);
                Jugador jugador2 = jugadorDAO.buscarJugadorPorId(idJugador2);

                lista.add(new Pareja(idPareja, jugador1, jugador2, idGrupo));
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
