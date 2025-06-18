package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JugadorDAO {
    private Connection conexion;

    public JugadorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarJugador(Jugador jugador) throws SQLException {
        String sql = "INSERT INTO Jugador (nombreJugador, dniJugador, telefonoJugador) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getDni());
            ps.setString(3, jugador.getTelefono());
            ps.executeUpdate();
        }
    }
}

