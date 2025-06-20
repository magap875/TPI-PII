package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import java.sql.*;

public class EstadisticasDAO {

    public Connection conexion;

    public EstadisticasDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // metodo para insertar una estadística en la bbdd
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

    // metodo para buscar una estadística por idPareja
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
}
