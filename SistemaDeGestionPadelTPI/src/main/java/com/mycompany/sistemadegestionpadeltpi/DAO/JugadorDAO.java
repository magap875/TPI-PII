package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private Connection conexion;
    private List<Jugador> jugadores = new ArrayList<>();
    
    public JugadorDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public JugadorDAO() {
    }

    public void insertarJugador(Jugador jugador) throws SQLException {
        String sql = "INSERT INTO Jugador (nombreJugador, dniJugador, telefonoJugador) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getDni());
            ps.setString(3, jugador.getTelefono());
            ps.executeUpdate();
            jugadores.add(jugador);
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
    
    
    
    public Jugador buscarPorNombre(String nombre) throws SQLException {
    String sql = "SELECT * FROM Jugador WHERE idJugador = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, nombre);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Jugador(
                    rs.getInt("idJugador"),
                    rs.getString("nombreJugador"),
                    rs.getString("dniJugador"),
                    rs.getString("telefonoJugador")
                );
            }
        }
    }
    return null;
}
    
    
}
