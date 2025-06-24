package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadisticaDAO {

    public Connection conexion;

    public EstadisticaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // insertamos estadisticas en la bbdd
    public void insertarEstadistica(Estadistica estadistica) throws SQLException {
        String sql = "INSERT INTO estadistica (idPareja, partidosJugados, partidosGanados, partidosPerdidos) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, estadistica.getIdPareja());
            ps.setInt(2, estadistica.getPartidosJugados());
            ps.setInt(3, estadistica.getPartidosGanados());
            ps.setInt(4, estadistica.getPartidosPerdidos());
            ps.executeUpdate();
        }
    }

    // buscamos estadisticas por pareja
    public Estadistica buscarEstadisticaPorIdPareja(int idPareja) throws SQLException {
        String sql = "SELECT * FROM estadistica WHERE idPareja = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPareja);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estadistica(
                            rs.getInt("idPareja"),
                            rs.getInt("partidosJugados"),
                            rs.getInt("partidosGanados"),
                            rs.getInt("partidosPerdidos")
                    );
                }
            }
        }
        return null;
    }

    // metodo de ayuda para obtener estadisticas por grupo
    public List<Estadistica> obtenerEstadisticasOrdenadasPorGrupo(String idGrupo) throws SQLException {
        List<Estadistica> lista = new ArrayList<>();

        String sql = """
        SELECT e.*
        FROM estadistica e
        JOIN pareja p ON e.idPareja = p.idPareja
        WHERE p.idGrupo = ?
        ORDER BY e.partidosGanados DESC
    """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idPareja = rs.getInt("idPareja");
                int jugados = rs.getInt("partidosJugados");
                int ganados = rs.getInt("partidosGanados");
                int perdidos = rs.getInt("partidosPerdidos");

                lista.add(new Estadistica(idPareja, jugados, ganados, perdidos));
            }
        }

        return lista;
    }

    // actualizamos estadisticas de una pareja
    public void actualizarEstadisticas(int idParejaGanadora, int idParejaPerdedora) throws SQLException {

        String sqlGanador = "UPDATE estadistica SET partidosJugados = partidosJugados + 1, partidosGanados = partidosGanados + 1 WHERE idPareja = ?";
        String sqlPerdedor = "UPDATE estadistica SET partidosJugados = partidosJugados + 1, partidosPerdidos = partidosPerdidos + 1 WHERE idPareja = ?";

        try (PreparedStatement psGanador = conexion.prepareStatement(sqlGanador); PreparedStatement psPerdedor = conexion.prepareStatement(sqlPerdedor)) {

            psGanador.setInt(1, idParejaGanadora);
            psGanador.executeUpdate();

            psPerdedor.setInt(1, idParejaPerdedora);
            psPerdedor.executeUpdate();
        }
    }

    public List<Estadistica> obtenerEstadisticasPorGrupoYTorneo(String idGrupo, int idTorneo) throws SQLException {
        List<Estadistica> ranking = new ArrayList<>();
        String sql
                = "SELECT e.idPareja, e.partidosJugados, e.partidosGanados, e.partidosPerdidos "
                + "FROM estadistica e "
                + "JOIN pareja p ON e.idPareja = p.idPareja "
                + "WHERE p.idGrupo = ? AND p.idTorneo = ? "
                + "ORDER BY e.partidosGanados DESC, (e.partidosJugados - e.partidosGanados) DESC";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            ps.setInt(2, idTorneo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPareja = rs.getInt("idPareja");
                    int jugados = rs.getInt("partidosJugados");
                    int ganados = rs.getInt("partidosGanados");
                    int perdidos = rs.getInt("partidosPerdidos");
                    ranking.add(new Estadistica(idPareja, jugados, ganados, perdidos));
                }
            }
        }

        return ranking;
    }

    // creamos una estadistica inicial a la pareja
    public void insertarEstadisticaInicial(int idPareja) throws SQLException {
        String sql = "INSERT INTO estadistica (idPareja, partidosJugados, partidosGanados, partidosPerdidos) VALUES (?, 0, 0, 0)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPareja);
            ps.executeUpdate();
        }
    }

}
