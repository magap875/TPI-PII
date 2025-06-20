package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Torneo;
import java.sql.*;

public class TorneoDAO {

    public Connection conexion;

    public TorneoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para insertar un torneo en la base de datos
    public void insertarTorneo(Torneo torneo) throws SQLException {
        String sql = "INSERT INTO torneo (nombre, categoria, capacidad_maxima) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, torneo.getNombre());
            ps.setString(2, torneo.getCategoria());
            ps.setInt(3, torneo.getCapacidadMaxima());
            ps.executeUpdate();
        }
    }

    // Método para buscar un torneo por su ID
    public Torneo buscarTorneoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM torneo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Torneo(
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("capacidad_maxima")
                    );
                }
            }
        }
        return null;
    }
}
