package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.EstadisticaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.PartidoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.TorneoDAO;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Torneo;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaAdministrador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorAdministrador {

    private final VistaAdministrador vistaAdministrador = new VistaAdministrador();
    private final SistemaDeGestionPadelTPI sistema;
    private final Connection conexion;

    public ControladorAdministrador(SistemaDeGestionPadelTPI sistema) {
        this.sistema = sistema;
        this.conexion = sistema.getConexion();
    }

    public void ejecutarMenuAdministrador() {
        int opcion;
        do {
            opcion = vistaAdministrador.mostrarMenuAdministrador();
            switch (opcion) {
                case 1 ->
                    crearTorneo();
                case 2 ->
                    generarPartidosPorGrupo();
                case 3 ->
                    cargarResultado();
                case 4 ->
                    consultarPartidosDelTorneo();
                case 5 ->
                    verClasificacion();
            }
        } while (opcion != 0);
    }

    // metodo de creacion de torneo y grupos
    public void crearTorneo() {
        try {
            int idTorneo = Integer.parseInt(vistaAdministrador.pedirDato("ID del torneo: "));
            String nombre = vistaAdministrador.pedirDato("NOMBRE del torneo: ");
            String categoria = vistaAdministrador.pedirDato("CATEGORIA del torneo: ");
            int cuposDisponibles = Integer.parseInt(vistaAdministrador.pedirDato("Cantidad de PAREJAS del torneo: "));

            Torneo torneo = new Torneo(idTorneo, nombre, categoria, cuposDisponibles, this.conexion);

            TorneoDAO torneoDAO = new TorneoDAO(conexion);
            torneoDAO.insertarTorneo(torneo);

            int cantidadGrupos = cuposDisponibles / 3;
            GrupoDAO grupoDAO = new GrupoDAO(conexion);
            List<Grupo> grupos = new ArrayList<>();

            for (int i = 0; i < cantidadGrupos; i++) {
                char idGrupo = (char) ('A' + i);
                Grupo grupo = new Grupo(String.valueOf(idGrupo), idTorneo);
                grupoDAO.insertarGrupo(grupo);
                grupos.add(grupo);
            }

            vistaAdministrador.mensaje("Torneo y " + cantidadGrupos + " grupo(s) creados exitosamente.");

        } catch (NumberFormatException e) {
            vistaAdministrador.mensaje("Error: cupos disponibles debe ser un numero.");
        } catch (SQLException e) {
            vistaAdministrador.mensaje("Error al insertar en la base de datos: " + e.getMessage());
        }
    }

    // metodo para generar los partidos por grupo y torneo
    public void generarPartidosPorGrupo() {
        try {
            List<Pareja> parejas = sistema.getListaParejas();
            if (parejas.isEmpty()) {
                vistaAdministrador.mensaje("No hay parejas cargadas.");
                return;
            }

            PartidoDAO partidoDAO = sistema.getPartidoDAO();

            for (int i = 0; i < parejas.size(); i++) {
                for (int j = i + 1; j < parejas.size(); j++) {
                    Pareja p1 = parejas.get(i);
                    Pareja p2 = parejas.get(j);

                    // mismo torneo, mismo grupo
                    if (p1.getIdTorneo() == p2.getIdTorneo()
                            && p1.getIdGrupo().equals(p2.getIdGrupo())) {

                        // evitamos crear de nuevo si ya existe
                        boolean yaExiste = partidoDAO.existePartido(
                                p1.getIdPareja(),
                                p2.getIdPareja(),
                                p1.getIdTorneo()
                        );
                        if (!yaExiste) {
                            Partido partido = new Partido(0, p1, p2, "", p1.getIdTorneo(), p1.getIdGrupo());
                            partidoDAO.insertarPartido(partido);
                        }
                    }
                }
            }

            sistema.traerPartidosDesdeBD();
            vistaAdministrador.mensaje("Partidos generados correctamente.");

        } catch (Exception e) {
            vistaAdministrador.mensaje("Error al generar partidos: " + e.getMessage());
        }
    }

    public void cargarResultado() {
        try {

            int idTorneo = Integer.parseInt(
                    vistaAdministrador.pedirDato("Ingrese ID del torneo: ")
            );

            int idPartido = Integer.parseInt(
                    vistaAdministrador.pedirDato("Ingrese ID del partido: ")
            );
            String resultado = vistaAdministrador.pedirDato(
                    "Ingrese resultado (ej: 6-3, 6-4): "
            );

            // actualizamos resultado en BD
            sistema.getPartidoDAO().actualizarResultado(idPartido, resultado);

            // traemos el partido para validar que exista y pertenezca al torneo
            Partido partido = sistema.getPartidoDAO().buscarPartidoPorId(idPartido);
            if (partido == null || partido.getIdTorneo() != idTorneo) {
                vistaAdministrador.mensaje(
                        "No se encontro el partido con ID " + idPartido
                        + " en el torneo " + idTorneo + "."
                );
                return;
            }

            // ID de la pareja ganadora
            int ganador = Integer.parseInt(
                    vistaAdministrador.pedirDato(
                            "Ingrese ID de la pareja ganadora ("
                            + partido.getPareja1().getIdPareja() + " o "
                            + partido.getPareja2().getIdPareja() + "): "
                    )
            );

            int perdedor;
            if (ganador == partido.getPareja1().getIdPareja()) {
                perdedor = partido.getPareja2().getIdPareja();
            } else if (ganador == partido.getPareja2().getIdPareja()) {
                perdedor = partido.getPareja1().getIdPareja();
            } else {
                vistaAdministrador.mensaje("ID de pareja ganadora invalido.");
                return;
            }

            // actualizar estadisticas para ese torneo
            sistema.getEstadisticaDAO().actualizarEstadisticas(ganador, perdedor);
            sistema.traerPartidosDesdeBD();
            vistaAdministrador.mensaje("Resultado y estadisticas actualizadas correctamente.");

        } catch (Exception e) {
            vistaAdministrador.mensaje("Error al cargar resultado: " + e.getMessage());
        }
    }

    // consultar partidos programados, filtrados por torneo
    public void consultarPartidosDelTorneo() {
        try {

            int idTorneo = Integer.parseInt(
                    vistaAdministrador.pedirDato("Ingrese ID del torneo: ")
            );

            List<Partido> partidos = sistema.getListaPartidos().stream()
                    .filter(p -> p.getIdTorneo() == idTorneo)
                    .collect(Collectors.toList());

            if (partidos.isEmpty()) {
                vistaAdministrador.mensaje(
                        "No hay partidos cargados para el torneo " + idTorneo + "."
                );
                return;
            }

            vistaAdministrador.mensaje(
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

                vistaAdministrador.mensaje(
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
            vistaAdministrador.mensaje("Error al consultar partidos: " + e.getMessage());
        }
    }

    // consultamos clasificacion de un grupo *dentro de un torneo*
    public void verClasificacion() {
        try {

            int idTorneo = Integer.parseInt(
                    vistaAdministrador.pedirDato("Ingrese ID del torneo: ")
            );
            String idGrupo = vistaAdministrador.pedirDato(
                    "Ingrese el ID del grupo (ej: A): "
            );

            EstadisticaDAO estadisticaDAO = new EstadisticaDAO(sistema.getConexion());
            ParejaDAO parejaDAO = sistema.getParejaDAO();

            // obtenemos solo las estadisticas del grupo y torneo indicados
            List<Estadistica> ranking = estadisticaDAO
                    .obtenerEstadisticasPorGrupoYTorneo(idGrupo, idTorneo);

            if (ranking.isEmpty()) {
                vistaAdministrador.mensaje(
                        "No hay estadisticas para el torneo " + idTorneo
                        + ", grupo " + idGrupo + "."
                );
                return;
            }

            vistaAdministrador.mensaje(
                    "=== CLASIFICACION Torneo " + idTorneo + " - Grupo " + idGrupo + " ==="
            );

            // recorremos y mostramos cada pareja con su estadistica
            for (Estadistica e : ranking) {
                Pareja pareja = parejaDAO.buscarParejaPorId(e.getIdPareja());
                String n1 = pareja.getJugador1().getNombre();
                String n2 = pareja.getJugador2().getNombre();
                vistaAdministrador.mensaje(
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
            vistaAdministrador.mensaje("Error al mostrar clasificacion: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
