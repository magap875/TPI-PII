package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    private Connection conexion;

    public PartidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // metodo para cargar partidos a la bbdd
    public void insertarPartido(Partido partido) throws SQLException {
        String sql = "INSERT INTO Partido (idPareja1, idPareja2, resultado) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, partido.getPareja1().getIdPareja());
            ps.setInt(2, partido.getPareja2().getIdPareja());
            ps.setString(3, partido.getResultado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    partido.setIdPartido(rs.getInt(1));
                }
            }
        }
    }
}
