package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.EstadisticaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ControladorJugador {

    private final VistaJugador vistaJugador = new VistaJugador();
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
                    inscribirseATorneo();
                case 3 ->
                    consultarPartidosDelTorneo();
                case 4 ->
                    verClasificacion();
                case 5 ->
                    mostrarGanador();
            }
        } while (opcion != 0);
    }

    // registramos un jugador
    public void registrarJugador() {
        try {
            int id = Integer.parseInt(vistaJugador.pedirDato("Ingrese su ID: "));
            String nombre = vistaJugador.pedirDato("Ingrese su NOMBRE: ");
            String dni = vistaJugador.pedirDato("Ingrese su DNI: ");
            String telefono = vistaJugador.pedirDato("Ingrese su TELEFONO: ");

            Jugador jugador = new Jugador(id, nombre, dni, telefono);
            sistema.getJugadorDAO().insertarJugador(jugador);
            vistaJugador.mensaje("Registro exitoso.");

            sistema.traerJugadoresDesdeBD(); // actualizamos la lista en el sistema

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }

    // metodo para inscribirse a un torneo, que incluye el registro de la pareja
    public void inscribirseATorneo() {

        System.out.println("Jugadores disponibles: ");
        for (Jugador j : sistema.getListaJugadores()) {
            System.out.println(j);
        }

        vistaJugador.mensaje("*** REGISTRE LA PAREJA ***");

        try {
            int idTorneo = Integer.parseInt(vistaJugador.pedirDato("ID del torneo que desea inscribrse: "));
            int idPareja = Integer.parseInt(vistaJugador.pedirDato("ID de la pareja: "));
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
                List<String> idsGrupo = grupoDAO.obtenerIdsGrupoPorTorneo(idTorneo);

                if (idsGrupo.isEmpty()) {
                    vistaJugador.mensaje("No hay grupos disponibles.");
                    return;
                }

                String idGrupoAleatorio = idsGrupo.get(new Random().nextInt(idsGrupo.size()));
                Pareja pareja = new Pareja(idPareja, jugador1, jugador2, idTorneo, idGrupoAleatorio);

                sistema.getParejaDAO().insertarParejaYAsignarleGrupo(pareja, grupoDAO);

                vistaJugador.mensaje("Registro exitoso en el grupo " + pareja.getIdGrupo() + ".");

                sistema.traerParejasDesdeBD();

            } else {
                vistaJugador.mensaje("Uno o ambos jugadores no existen.");
            }

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }

    // consultar partidos programados, filtrados por torneo
    public void consultarPartidosDelTorneo() {
        try {
            int idTorneo = Integer.parseInt(
                    vistaJugador.pedirDato("Ingrese ID del torneo: ")
            );

            // obtenemos solo los partidos de ese torneo
            List<Partido> partidos = sistema.getListaPartidos().stream()
                    .filter(p -> p.getIdTorneo() == idTorneo)
                    .collect(Collectors.toList());

            if (partidos.isEmpty()) {
                vistaJugador.mensaje(
                        "No hay partidos cargados para el torneo " + idTorneo + "."
                );
                return;
            }

            vistaJugador.mensaje(
                    "=== PARTIDOS PROGRAMADOS (Torneo " + idTorneo + ") ==="
            );

            // recorremos y mostramos cada partido
            for (Partido partido : partidos) {
                int idP1 = partido.getPareja1().getIdPareja();
                String j1a = partido.getPareja1().getJugador1().getNombre();
                String j2a = partido.getPareja1().getJugador2().getNombre();

                int idP2 = partido.getPareja2().getIdPareja();
                String j1b = partido.getPareja2().getJugador1().getNombre();
                String j2b = partido.getPareja2().getJugador2().getNombre();

                String resultado = partido.getResultado().isEmpty()
                        ? "Pendiente"
                        : partido.getResultado();

                vistaJugador.mensaje(
                        String.format(
                                "Partido ID: %d | Pareja %d (%s y %s) | Pareja %d (%s y %s) | Grupo: %s | Resultado: %s",
                                partido.getIdPartido(),
                                idP1, j1a, j2a,
                                idP2, j1b, j2b,
                                partido.getIdGrupo(),
                                resultado
                        )
                );
            }
        } catch (Exception e) {
            vistaJugador.mensaje("Error al consultar partidos: " + e.getMessage());
        }
    }

    // consultar clasificacion por torneo y grupo
    public void verClasificacion() {
        try {
            // pedir datos de torneo y grupo
            int idTorneo = Integer.parseInt(
                    vistaJugador.pedirDato("Ingrese ID del torneo: ")
            );
            String idGrupo = vistaJugador.pedirDato(
                    "Ingrese el ID del grupo (ej: A): "
            );

            EstadisticaDAO estadisticaDAO = new EstadisticaDAO(sistema.getConexion());
            ParejaDAO parejaDAO = sistema.getParejaDAO();

            // obtener solo las estadisticas del grupo y torneo indicados
            List<Estadistica> ranking = estadisticaDAO
                    .obtenerEstadisticasPorGrupoYTorneo(idGrupo, idTorneo);

            if (ranking.isEmpty()) {
                vistaJugador.mensaje(
                        "No hay estadisticas para el torneo " + idTorneo
                        + ", grupo " + idGrupo + "."
                );
                return;
            }

            vistaJugador.mensaje(
                    "=== CLASIFICACION Torneo " + idTorneo + " - Grupo " + idGrupo + " ==="
            );

            // recorrer y mostrar cada pareja con su estadistica
            for (Estadistica e : ranking) {
                Pareja pareja = parejaDAO.buscarParejaPorId(e.getIdPareja());
                String n1 = pareja.getJugador1().getNombre();
                String n2 = pareja.getJugador2().getNombre();
                vistaJugador.mensaje(
                        String.format(
                                "Pareja %d (%s y %s): PJ: %d | PG: %d | PP: %d",
                                e.getIdPareja(), n1, n2,
                                e.getPartidosJugados(),
                                e.getPartidosGanados(),
                                e.getPartidosPerdidos()
                        )
                );
            }

        } catch (Exception e) {
            vistaJugador.mensaje("Error al mostrar clasificacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // metodo que creamos para obtener una pareja ganadora
    public Pareja obtenerParejaGanadora(int idTorneo) throws SQLException {
        Estadistica estadisticaGanadora = sistema.getEstadisticaDAO().obtenerParejaGanadora(idTorneo);

        if (estadisticaGanadora != null) {
            return sistema.getParejaDAO().buscarParejaPorId(estadisticaGanadora.getIdPareja());
        }

        return null;
    }

    // metodo para posteriormente mostrarla
    public void mostrarGanador() {
        try {
            int idTorneo = Integer.parseInt(vistaJugador.pedirDato("Ingrese el ID del torneo: "));
            Pareja campeona = obtenerParejaGanadora(idTorneo);

            if (campeona != null) {
                Estadistica estadistica = sistema.getEstadisticaDAO().buscarEstadisticaPorIdPareja(campeona.getIdPareja());

                if (estadistica != null) {
                    vistaJugador.mensaje("");
                    vistaJugador.mensaje("Criterios para desempate:");
                    vistaJugador.mensaje("1) Mas partidos ganados.");
                    vistaJugador.mensaje("2) Mayor diferencia entre ganados y perdidos.");
                    vistaJugador.mensaje("3) Menor cantidad de partidos jugados.\n");
                    
                    vistaJugador.mensaje("");
                    vistaJugador.mensaje("La PAREJA CAMPEONA es: "
                            + campeona.getJugador1().getNombre() + " y "
                            + campeona.getJugador2().getNombre());
                    
                    vistaJugador.mensaje("");
                    vistaJugador.mensaje("Estadisticas:");
                    vistaJugador.mensaje("a. Partidos jugados: " + estadistica.getPartidosJugados());
                    vistaJugador.mensaje("b. Partidos ganados: " + estadistica.getPartidosGanados());
                    vistaJugador.mensaje("c. Partidos perdidos: " + estadistica.getPartidosPerdidos());
                } else {
                    vistaJugador.mensaje("No se encontraron estadisticas para la pareja ganadora.");
                }

            } else {
                vistaJugador.mensaje("No se pudo determinar una pareja ganadora.");
            }

        } catch (SQLException e) {
            vistaJugador.mensaje("Error al obtener la pareja ganadora: " + e.getMessage());
        }
    }
}
