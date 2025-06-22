package com.mycompany.sistemadegestionpadeltpi.Main;

import com.mycompany.sistemadegestionpadeltpi.Controlador.ControladorGeneral;
import com.mycompany.sistemadegestionpadeltpi.DAO.EstadisticaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.PartidoDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class SistemaDeGestionPadelTPI {

    private List<Jugador> listaJugadores;
    private List<Pareja> listaParejas;
    private List<Partido> listaPartidos;
    public JugadorDAO jugadorDAO;
    public ParejaDAO parejaDAO;
    public PartidoDAO partidoDAO;
    public EstadisticaDAO estadisticaDAO;
    private Connection conexion;

    // inicializamos la conexión y los DAO
    public SistemaDeGestionPadelTPI(Connection conexion) {
        this.conexion = conexion;
        this.jugadorDAO = new JugadorDAO(conexion);
        this.parejaDAO = new ParejaDAO(conexion);
        this.partidoDAO = new PartidoDAO(conexion);
        this.estadisticaDAO = new EstadisticaDAO(conexion);
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistemapadel", "root", "popi2025"
            );

            SistemaDeGestionPadelTPI sistema = new SistemaDeGestionPadelTPI(con);

            // traemos los datos de la bbdd
            sistema.traerJugadoresDesdeBD();
            sistema.traerParejasDesdeBD();
            sistema.traerPartidosDesdeBD();

            // printeamos para verificar que todo salio bien
            System.out.println("=== LISTA DE JUGADORES ===");
            for (Jugador j : sistema.getListaJugadores()) {
                System.out.println(j);
            }

            System.out.println("=== LISTA DE PAREJAS ===");
            for (Pareja p : sistema.getListaParejas()) {
                System.out.println(p);
            }

            System.out.println("=== LISTA DE PARTIDOS ===");
            for (Partido p : sistema.getListaPartidos()) {
                System.out.println(p);
            }

            // ejecucion del menu general
            ControladorGeneral controlador = new ControladorGeneral(sistema);
            controlador.ejecutarMenuGeneral();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void traerJugadoresDesdeBD() {
        listaJugadores = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM jugador";
            var stmt = conexion.createStatement();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombreJugador");
                String dni = rs.getString("dniJugador");
                String telefono = rs.getString("telefonoJugador");

                Jugador jugador = new Jugador(id, nombre, dni, telefono);
                listaJugadores.add(jugador);
            }

        } catch (Exception e) {
            System.out.println("Error al traer jugadores: " + e.getMessage());
        }
    }

    public void traerParejasDesdeBD() {
        listaParejas = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM pareja";
            var stmt = conexion.createStatement();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idPareja = rs.getInt("idPareja");
                int idJugador1 = rs.getInt("idJugador1");
                int idJugador2 = rs.getInt("idJugador2");
                String idGrupo = rs.getString("idGrupo");
                int idTorneo = rs.getInt("idTorneo");
                Jugador jugador1 = jugadorDAO.buscarJugadorPorId(idJugador1);
                Jugador jugador2 = jugadorDAO.buscarJugadorPorId(idJugador2);

                Pareja pareja = new Pareja(idPareja, jugador1, jugador2,idTorneo, idGrupo);
                listaParejas.add(pareja);
            }

        } catch (Exception e) {
            System.out.println("Error al traer parejas: " + e.getMessage());
        }
    }

    public void traerPartidosDesdeBD() {
        listaPartidos = new java.util.ArrayList<>();
        try {
            String sql = "SELECT * FROM partido";
            var stmt = conexion.createStatement();
            var rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idPartido");
                int idPareja1 = rs.getInt("idPareja1");
                int idPareja2 = rs.getInt("idPareja2");
                String idGrupo = rs.getString("idGrupo");
                String resultado = rs.getString("resultado");
                int idTorneo = rs.getInt("idTorneo");
                Pareja pareja1 = parejaDAO.buscarParejaPorId(idPareja1);
                Pareja pareja2 = parejaDAO.buscarParejaPorId(idPareja2);

                listaPartidos.add(new Partido(id, pareja1, pareja2, resultado,idTorneo, idGrupo));
            }

        } catch (Exception e) {
            System.out.println("Error al traer partidos: " + e.getMessage());
        }
    }

    // metodos varios de ayuda
    public Connection getConexion() {
        return conexion;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public List<Pareja> getListaParejas() {
        return listaParejas;
    }

    public List<Partido> getListaPartidos() {
        return listaPartidos;
    }

    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    public ParejaDAO getParejaDAO() {
        return parejaDAO;
    }

    public PartidoDAO getPartidoDAO() {
        return partidoDAO;
    }

    public EstadisticaDAO getEstadisticaDAO() {
        return estadisticaDAO;
    }

    public void setEstadisticaDAO(EstadisticaDAO estadisticaDAO) {
        this.estadisticaDAO = estadisticaDAO;
    }
}
