package com.mycompany.sistemadegestionpadeltpi.DAO;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.sql.*;

public class PartidoDAO {

    private final Connection conexion;

    public PartidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // cargar partido en bbdd
   public void insertarPartido(Partido partido) throws SQLException {
    String sql = "INSERT INTO partido (idPareja1, idPareja2, resultado, idTorneo, idGrupo) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, partido.getPareja1().getIdPareja());
        ps.setInt(2, partido.getPareja2().getIdPareja());
        ps.setString(3, partido.getResultado());
        ps.setInt(4, partido.getIdTorneo());      // <-- nuevo campo obligatorio
        ps.setString(5, partido.getIdGrupo());
        ps.executeUpdate();
    }
}


    // buscar por partido por id
    public Partido buscarPartidoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM partido WHERE idPartido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idPareja1 = rs.getInt("idPareja1");
                    int idPareja2 = rs.getInt("idPareja2");
                    String resultado = rs.getString("resultado");
                    String idGrupo = rs.getString("idGrupo");
                    int idTorneo = rs.getInt("idTorneo");
                    ParejaDAO parejaDAO = new ParejaDAO(conexion);
                    Pareja p1 = parejaDAO.buscarParejaPorId(idPareja1);
                    Pareja p2 = parejaDAO.buscarParejaPorId(idPareja2);

                    return new Partido(id, p1, p2, resultado, idTorneo,idGrupo);
                }
            }
        }
        return null;
    }
    public boolean existePartido(int idPareja1, int idPareja2, int idTorneo) throws SQLException {
    String sql = "SELECT 1 FROM partido WHERE idPareja1 = ? AND idPareja2 = ? AND idTorneo = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idPareja1);
        ps.setInt(2, idPareja2);
        ps.setInt(3, idTorneo);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }
    }
}

    // actualizar el resultado de un partido
    public void actualizarResultado(int idPartido, String resultado) throws SQLException {
        String sql = "UPDATE partido SET resultado = ? WHERE idPartido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, resultado);
            ps.setInt(2, idPartido);
            ps.executeUpdate();
        }
    }
    
}
