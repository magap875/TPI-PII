package com.mycompany.sistemadegestionpadeltpi.DAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import java.sql.*;
import java.util.*;
public class GrupoDAO {

    private Connection conexion;

    public GrupoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarGrupo(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO Grupo (idGrupo) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, grupo.getIdGrupo());
            ps.executeUpdate();
        }
    }
    public List<String> obtenerTodosLosIdGrupo() throws SQLException {
    List<String> ids = new ArrayList<>();
    String sql = "SELECT idGrupo FROM Grupo";
    try (PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            ids.add(rs.getString("idGrupo"));
        }
    }
    return ids;
}

    public Grupo buscarGrupoPorId(String idGrupo) throws SQLException {
        String sql = "SELECT * FROM Grupo WHERE idGrupo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Grupo(rs.getString("idGrupo"));
                }
            }
        }
        return null;
    }

}

