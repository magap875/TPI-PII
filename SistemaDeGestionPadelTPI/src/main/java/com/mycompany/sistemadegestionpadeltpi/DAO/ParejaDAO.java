package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParejaDAO {

    public Connection conexion;
    private List<Pareja> parejas = new ArrayList<>();

    public ParejaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ParejaDAO() {
    }

    public void insertarPareja(Pareja pareja) throws SQLException {
        String sql = "INSERT INTO Pareja (idJugador1, idJugador2) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pareja.getJugador1().getId());
            ps.setInt(2, pareja.getJugador2().getId());
            ps.executeUpdate();
        }
    }
    
    public Pareja buscarParejaPorId(int idPareja) {
    Pareja pareja = null;
    try {
        String sql = "SELECT * FROM pareja WHERE idPareja = ?";
        PreparedStatement pstmt = conexion.prepareStatement(sql);
        pstmt.setInt(1, idPareja);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int idJugador1 = rs.getInt("idJugador1");
            int idJugador2 = rs.getInt("idJugador2");

            // Supongo que tenés un JugadorDAO para traer los objetos Jugador:
            JugadorDAO jugadorDAO = new JugadorDAO(conexion);
            Jugador jugador1 = jugadorDAO.buscarJugadorPorId(idJugador1);
            Jugador jugador2 = jugadorDAO.buscarJugadorPorId(idJugador2);

            pareja = new Pareja(idPareja, jugador1, jugador2);
        }

    } catch (SQLException e) {
        System.out.println("Error al buscar pareja: " + e.getMessage());
    }
    return pareja;
}
    
}
