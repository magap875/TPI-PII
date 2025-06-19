package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import java.util.Random;

public class ControladorJugador {

    private VistaJugador vistaJugador = new VistaJugador();
    private VistaGeneral vistaGeneral = new VistaGeneral();
    private Connection conexion;
    private List<Jugador> listaJugadores;
    private JugadorDAO jugadorDAO;
    private Scanner s = new Scanner(System.in);
    private ParejaDAO parejaDAO;
    private SistemaDeGestionPadelTPI sistema = new SistemaDeGestionPadelTPI();

    public ControladorJugador(Connection conexion) {
        this.conexion = conexion;
        this.jugadorDAO = new JugadorDAO(conexion);
        this.parejaDAO = new ParejaDAO(conexion);
        this.vistaJugador = new VistaJugador();
        sistema.traerJugadoresDesdeBD(conexion);
        this.listaJugadores = new ArrayList<>(sistema.getListaJugadores());
    }

    public void ejecutarMenuJugador() {
        int opcion;
        do {
            opcion = vistaJugador.mostrarMenuJugador();
            switch (opcion) {
                case 1 ->
                    registrarJugador();
                case 2 ->
                    consultarPartidos();
                case 3 ->
                    verResultados();
                case 4 ->
                    verClasificacion();
                case 5 ->
                    inscribirseATorneo();

            }
        } while (opcion != 0);
    }

    public void registrarJugador() {
        try {
            int id = Integer.parseInt(vistaJugador.pedirDato("Ingrese su id: "));
            String nombre = vistaJugador.pedirDato("Ingrese su nombre: ");
            String dni = vistaJugador.pedirDato("Ingrese su dni: ");
            String telefono = vistaJugador.pedirDato("Ingrese su telefono: ");

            Jugador jugador = new Jugador(id, nombre, dni, telefono);
            JugadorDAO dao = new JugadorDAO(conexion);
            dao.insertarJugador(jugador);
            vistaJugador.mensaje("Registro exitoso.");

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }

    public void consultarPartidos() {

    }

    public void verResultados() {

    }

    public void verClasificacion() {

    }

   public void inscribirseATorneo() {
    vistaJugador.mensaje("*** Registre la pareja ***");

    try {
        int idPareja = Integer.parseInt(vistaJugador.pedirDato("ID de la pareja: "));
        int idIntegrante1 = Integer.parseInt(vistaJugador.pedirDato("ID del primer jugador: "));
        int idIntegrante2 = Integer.parseInt(vistaJugador.pedirDato("ID del segundo jugador: "));
        
        Jugador jugador1 = null;
        Jugador jugador2 = null;

        for (Jugador j : listaJugadores) {
            if (j.getId() == idIntegrante1) {
                jugador1 = j;
            } else if (j.getId() == idIntegrante2) {
                jugador2 = j;
            }
        }

        if (jugador1 != null && jugador2 != null) {
            // Obtener grupo aleatorio
            GrupoDAO grupoDAO = new GrupoDAO(conexion);
            List<String> idsGrupo = grupoDAO.obtenerTodosLosIdGrupo();

            if (idsGrupo.isEmpty()) {
                vistaJugador.mensaje("No hay grupos disponibles.");
                return;
            }

            String idGrupoAleatorio = idsGrupo.get(new Random().nextInt(idsGrupo.size()));

            // Crear pareja con grupo asignado
            Pareja pareja = new Pareja(idPareja, jugador1, jugador2,null);
            pareja.setIdGrupo(idGrupoAleatorio);
            GrupoDAO grupoDao=new GrupoDAO(conexion);
            ParejaDAO dao = new ParejaDAO(conexion);
            dao.insertarPareja(pareja,grupoDao);

            vistaJugador.mensaje("Registro exitoso en el grupo " + idGrupoAleatorio + ".");
        } else {
            vistaJugador.mensaje("Uno o ambos jugadores no existen.");
        }

    } catch (Exception e) {
        vistaJugador.mensaje("Error al registrar: " + e.getMessage());
    }
}
}
