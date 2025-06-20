package com.mycompany.sistemadegestionpadeltpi.DAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.sql.*;
import java.util.*;
public class GrupoDAO {

    private Connection conexion;

    public GrupoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarGrupo(Grupo grupo) throws SQLException {
    String sql = "INSERT INTO grupo (idGrupo, idPareja1, idPareja2, idPareja3) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, grupo.getIdGrupo());
        ps.setObject(2, grupo.getIdPareja1());
        ps.setObject(3, grupo.getIdPareja2());
        ps.setObject(4, grupo.getIdPareja3());
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
    String sql = "SELECT * FROM grupo WHERE idGrupo = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setString(1, idGrupo);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String id = rs.getString("idGrupo");
                Integer idPareja1 = rs.getObject("idPareja1") != null ? rs.getInt("idPareja1") : null;
                Integer idPareja2 = rs.getObject("idPareja2") != null ? rs.getInt("idPareja2") : null;
                Integer idPareja3 = rs.getObject("idPareja3") != null ? rs.getInt("idPareja3") : null;

                return new Grupo(id, idPareja1, idPareja2, idPareja3);
            }
        }
    }
    return null;
}
public void asignarParejaAGrupo(Pareja pareja) throws SQLException {
    String idGrupo = pareja.getIdGrupo();
    int idPareja = pareja.getIdPareja();

    String sqlSelect = "SELECT * FROM grupo WHERE idGrupo = ?";
    try (PreparedStatement psSelect = conexion.prepareStatement(sqlSelect)) {
        psSelect.setString(1, idGrupo);
        ResultSet rs = psSelect.executeQuery();

        if (rs.next()) {
            int p1 = rs.getInt("idPareja1");
            int p2 = rs.getInt("idPareja2");
            int p3 = rs.getInt("idPareja3");

            String sqlUpdate = null;

            if (p1 == 0) {
                sqlUpdate = "UPDATE grupo SET idPareja1 = ? WHERE idGrupo = ?";
            } else if (p2 == 0) {
                sqlUpdate = "UPDATE grupo SET idPareja2 = ? WHERE idGrupo = ?";
            } else if (p3 == 0) {
                sqlUpdate = "UPDATE grupo SET idPareja3 = ? WHERE idGrupo = ?";
            } else {
                throw new SQLException("El grupo " + idGrupo + " ya tiene 3 parejas.");
            }

            try (PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, idPareja);
                psUpdate.setString(2, idGrupo);
                psUpdate.executeUpdate();
            }
        }
    }
}


}

