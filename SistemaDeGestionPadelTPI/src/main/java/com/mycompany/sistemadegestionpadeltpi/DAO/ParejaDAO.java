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

    private Connection conexion;
    private List<Pareja> parejas = new ArrayList<>();

    public ParejaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public ParejaDAO() {
    }

    public void insertarPareja(Pareja pareja) throws SQLException {
        String sql = "INSERT INTO Pareja (jugador1, jugador2) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, pareja.getNombreJugador1());
            ps.setString(2, pareja.getNombreJugador2());
            ps.executeUpdate();
            parejas.add(pareja);
        }
    }

    public List<Pareja> getParejas() {
        return parejas;
    }
    
    public List<Pareja> obtenerTodas() throws SQLException {
    List<Pareja> parejas = new ArrayList<>();
    String sql = "SELECT * FROM Pareja";

    try (PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Pareja pareja = new Pareja(
                rs.getInt("idPareja"),
                rs.getString("jugador1"),
                rs.getString("jugador2")
            );
            parejas.add(pareja);
        }
    }

    return parejas;
}


}
