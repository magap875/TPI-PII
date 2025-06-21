package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;
import java.util.List;
import java.util.Random;

public class ControladorJugador {

    private final VistaJugador vistaJugador = new VistaJugador();
    private final VistaGeneral vistaGeneral = new VistaGeneral();
    private final SistemaDeGestionPadelTPI sistema;

    public ControladorJugador(SistemaDeGestionPadelTPI sistema) {
        this.sistema = sistema;

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
    
    // registramos un jugador
    public void registrarJugador() {
        try {
            int id = Integer.parseInt(vistaJugador.pedirDato("Ingrese su id: "));
            String nombre = vistaJugador.pedirDato("Ingrese su nombre: ");
            String dni = vistaJugador.pedirDato("Ingrese su dni: ");
            String telefono = vistaJugador.pedirDato("Ingrese su telefono: ");

            Jugador jugador = new Jugador(id, nombre, dni, telefono);
            sistema.getJugadorDAO().insertarJugador(jugador);
            vistaJugador.mensaje("Registro exitoso.");

            sistema.traerJugadoresDesdeBD(); // actualizamos la lista en el sistema

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

    // metodo para inscribirse a un torneo, que incluye el registro de la pareja
    public void inscribirseATorneo() {
        vistaJugador.mensaje("*** Registre la pareja ***");

        try {
            int idIntegrante1 = Integer.parseInt(vistaJugador.pedirDato("ID del primer jugador: "));
            int idIntegrante2 = Integer.parseInt(vistaJugador.pedirDato("ID del segundo jugador: "));

            if (idIntegrante1 == idIntegrante2) {
                vistaJugador.mensaje("Los jugadores deben ser distintos.");
                return;
            }

            List<Jugador> jugadores = sistema.getListaJugadores();
            Jugador jugador1 = null, jugador2 = null;

            for (Jugador j : jugadores) {
                if (j.getId() == idIntegrante1) {
                    jugador1 = j;
                }
                if (j.getId() == idIntegrante2) {
                    jugador2 = j;
                }
            }

            if (jugador1 != null && jugador2 != null) {
                GrupoDAO grupoDAO = new GrupoDAO(sistema.getConexion());
                List<String> idsGrupo = grupoDAO.obtenerTodosLosIdGrupo();

                if (idsGrupo.isEmpty()) {
                    vistaJugador.mensaje("No hay grupos disponibles.");
                    return;
                }

                String idGrupoAleatorio = idsGrupo.get(new Random().nextInt(idsGrupo.size()));
                Pareja pareja = new Pareja(0, jugador1, jugador2, idGrupoAleatorio);

                sistema.getParejaDAO().insertarPareja(pareja, grupoDAO);
                vistaJugador.mensaje("Registro exitoso en el grupo " + idGrupoAleatorio + ".");

                sistema.traerParejasDesdeBD();

            } else {
                vistaJugador.mensaje("Uno o ambos jugadores no existen.");
            }

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }
}
