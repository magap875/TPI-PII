package com.mycompany.sistemadegestionpadeltpi.Main;

import com.mycompany.sistemadegestionpadeltpi.Controlador.ControladorGeneral;
import com.mycompany.sistemadegestionpadeltpi.Controlador.ControladorAdministrador;
import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
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
    private Connection conexion;

    public SistemaDeGestionPadelTPI(Connection conexion) {
        this.conexion = conexion;
        this.jugadorDAO = new JugadorDAO(conexion);
        this.parejaDAO = new ParejaDAO(conexion);
    }

    public static void main(String[] args) {
        try {
            // 1. Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Conectarse a la base de datos
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistemapadel", "root", "frodo1234"
            );

            // 3. Crear instancia del sistema pasando la conexión
            SistemaDeGestionPadelTPI sistema = new SistemaDeGestionPadelTPI(con);

            // 4. Cargar datos desde la base sin pasar Connection a métodos
            sistema.traerJugadoresDesdeBD();
            sistema.traerParejasDesdeBD();
            sistema.traerPartidosDesdeBD();

            // 5. Mostrar los datos cargados
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

            // 6. Ejecutar el menú general
            ControladorGeneral controlador = new ControladorGeneral(con);
            controlador.ejecutarMenuGeneral();

            // 7. Cerrar conexión
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ahora los métodos sin parámetro Connection
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
                Jugador jugador1 = jugadorDAO.buscarJugadorPorId(idJugador1);
                Jugador jugador2 = jugadorDAO.buscarJugadorPorId(idJugador2);

                Pareja pareja = new Pareja(idPareja, jugador1, jugador2, idGrupo);
                listaParejas.add(pareja);
            }

        } catch (Exception e) {
            System.out.println("Error al traer parejas: " + e.getMessage());
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

                Pareja pareja1 = parejaDAO.buscarParejaPorId(idPareja1);
                Pareja pareja2 = parejaDAO.buscarParejaPorId(idPareja2);

                listaPartidos.add(new Partido(id, pareja1, pareja2, resultado, idGrupo));
            }

        } catch (Exception e) {
            System.out.println("Error al traer partidos: " + e.getMessage());
        }
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
}
