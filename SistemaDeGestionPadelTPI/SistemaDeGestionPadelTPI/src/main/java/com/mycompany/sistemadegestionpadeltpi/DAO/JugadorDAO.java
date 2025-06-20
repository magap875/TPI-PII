package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import java.sql.*;
import java.util.*;

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

    public Jugador buscarJugadorPorId(int id) throws SQLException {
        String sql = "SELECT * FROM jugador WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Jugador(
                        rs.getInt("id"),
                        rs.getString("nombreJugador"),
                        rs.getString("dniJugador"),
                        rs.getString("telefonoJugador")
                    );
                }
            }
        }
        return null;
    }

    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT * FROM jugador";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombreJugador");
                String dni = rs.getString("dniJugador");
                String telefono = rs.getString("telefonoJugador");

                lista.add(new Jugador(id, nombre, dni, telefono));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener jugadores: " + e.getMessage());
        }

        return lista;
    }
}
