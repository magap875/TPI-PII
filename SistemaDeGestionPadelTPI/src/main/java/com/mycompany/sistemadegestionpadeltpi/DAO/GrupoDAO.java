package com.mycompany.sistemadegestionpadeltpi.DAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import java.sql.*;
import java.util.*;

public class GrupoDAO {

    private Connection conexion;

    public GrupoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // cargar grupos a la bbdd
    public void insertarGrupo(Grupo grupo) throws SQLException {
        String sql = "INSERT INTO grupo (idGrupo, idTorneo) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, grupo.getIdGrupo());
            ps.setInt(2,grupo.getIdTorneo());
            ps.executeUpdate();
        }
    }
    
   
    public List<String> obtenerIdsGrupoPorTorneo(int idTorneo) throws SQLException {
    List<String> grupos = new ArrayList<>();
    String sql = "SELECT idGrupo FROM grupo WHERE idTorneo = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idTorneo);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                grupos.add(rs.getString("idGrupo"));
                }
            }
        }
        return grupos;  
    }

    // buscamos grupos por id
    public Grupo buscarGrupoPorId(String idGrupo) throws SQLException {
        String sql = "SELECT * FROM grupo WHERE idGrupo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("idGrupo");
                    int idTorneo=rs.getInt("idTorneo");
                    return new Grupo(id,idTorneo);
                }
            }
        }
        return null;
    }
}
