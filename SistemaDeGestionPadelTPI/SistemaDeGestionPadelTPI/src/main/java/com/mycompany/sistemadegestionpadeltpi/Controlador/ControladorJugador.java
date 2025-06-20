package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorJugador {

    private VistaJugador vistaJugador;
    private VistaGeneral vistaGeneral;
    private Connection conexion;
    private List<Jugador> listaJugadores;
    private JugadorDAO jugadorDAO;
    private ParejaDAO parejaDAO;
    private GrupoDAO grupoDAO;

    public ControladorJugador(Connection conexion) {
        this.conexion = conexion;
        this.vistaJugador = new VistaJugador();
        this.vistaGeneral = new VistaGeneral();
        this.jugadorDAO = new JugadorDAO(conexion);
        this.parejaDAO = new ParejaDAO(conexion);
        this.grupoDAO = new GrupoDAO(conexion);
        this.listaJugadores = jugadorDAO.obtenerTodosLosJugadores();
    }

    public void ejecutarMenuJugador() {
        int opcion;
        do {
            opcion = vistaJugador.mostrarMenuJugador();
            switch (opcion) {
                case 1 -> registrarJugador();
                case 2 -> consultarPartidos();
                case 3 -> verResultados();
                case 4 -> verClasificacion();
                case 5 -> inscribirseATorneo();
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
            jugadorDAO.insertarJugador(jugador);
            vistaJugador.mensaje("Registro exitoso.");

            // Refrescar lista
            this.listaJugadores = jugadorDAO.obtenerTodosLosJugadores();

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }

    public void consultarPartidos() {
        // A implementar
    }

    public void verResultados() {
        // A implementar
    }

    public void verClasificacion() {
        // A implementar
    }

    public void inscribirseATorneo() {
        vistaJugador.mensaje("*** Registre la pareja ***");

        try {
            int idPareja = Integer.parseInt(vistaJugador.pedirDato("ID de la pareja: "));
            int idIntegrante1 = Integer.parseInt(vistaJugador.pedirDato("ID del primer jugador: "));
            int idIntegrante2 = Integer.parseInt(vistaJugador.pedirDato("ID del segundo jugador: "));

            if (idIntegrante1 == idIntegrante2) {
                vistaJugador.mensaje("Los jugadores deben ser distintos.");
                return;
            }

            Jugador jugador1 = null;
            Jugador jugador2 = null;

            for (Jugador j : listaJugadores) {
                if (j.getId() == idIntegrante1) jugador1 = j;
                if (j.getId() == idIntegrante2) jugador2 = j;
            }

            if (jugador1 != null && jugador2 != null) {
                List<String> idsGrupo = grupoDAO.obtenerTodosLosIdGrupo();

                if (idsGrupo.isEmpty()) {
                    vistaJugador.mensaje("No hay grupos disponibles.");
                    return;
                }

                String idGrupoAleatorio = idsGrupo.get(new Random().nextInt(idsGrupo.size()));
                Pareja pareja = new Pareja(idPareja, jugador1, jugador2, idGrupoAleatorio);

                parejaDAO.insertarPareja(pareja, grupoDAO);

                vistaJugador.mensaje("Registro exitoso en el grupo " + idGrupoAleatorio + ".");
            } else {
                vistaJugador.mensaje("Uno o ambos jugadores no existen.");
            }

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }
}
