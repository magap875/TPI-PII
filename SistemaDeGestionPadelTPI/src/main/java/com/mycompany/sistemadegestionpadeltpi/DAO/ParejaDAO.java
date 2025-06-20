package com.mycompany.sistemadegestionpadeltpi.DAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
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

    // metodo para cargar parejas a la BBDD
  public void insertarPareja(Pareja pareja, GrupoDAO grupoDAO) throws SQLException {
    // Paso 1: buscar grupo con menos de 3 parejas
    String grupoDisponible = buscarGrupoConEspacio(grupoDAO);

    if (grupoDisponible == null) {
        throw new SQLException("No hay grupos disponibles con espacio.");
    }

    // Asignar grupo a la pareja
    pareja.setIdGrupo(grupoDisponible);

    // Paso 2: insertar pareja en la base
    String sql = "INSERT INTO pareja (idPareja, idJugador1, idJugador2, idGrupo) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, pareja.getIdPareja());
        ps.setInt(2, pareja.getJugador1().getId());
        ps.setInt(3, pareja.getJugador2().getId());
        ps.setString(4, pareja.getIdGrupo());
        ps.executeUpdate();
    }
}
    private String buscarGrupoConEspacio(GrupoDAO grupoDAO) throws SQLException {
    List<String> idsGrupo = grupoDAO.obtenerTodosLosIdGrupo();

    for (String idGrupo : idsGrupo) {
        // Contar cuántas parejas hay en ese grupo
        String sql = "SELECT COUNT(*) AS cantidad FROM pareja WHERE idGrupo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int cantidad = rs.getInt("cantidad");
                    if (cantidad < 3) {
                        return idGrupo; // Este grupo tiene lugar
                    }
                }
            }
        }
    }

    return null; // No hay grupos con espacio
} 
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
}
