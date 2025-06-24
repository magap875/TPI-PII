package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.EstadisticaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaUsuario;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorUsuario {

    private final VistaUsuario vistaUsuario = new VistaUsuario();
    private final SistemaDeGestionPadelTPI sistema;

    public ControladorUsuario(SistemaDeGestionPadelTPI sistema) {
        this.sistema = sistema;

    }

    public void ejecutarMenuUsuario() {
        int opcion = 0;
        do {
            opcion = vistaUsuario.mostrarMenuUsuario();
            switch (opcion) {
                case 1 ->
                    consultarPartidosDelTorneo();
                case 2 ->
                    verClasificacion();
                case 3 ->
                    mostrarGanador();

            }
        } while (opcion != 0);
    }

    public void consultarPartidosDelTorneo() {
        try {
            // pedir al administrador el ID del torneo
            int idTorneo = Integer.parseInt(
                    vistaUsuario.pedirDato("Ingrese ID del torneo: ")
            );

            // obtener solo los partidos de ese torneo
            List<Partido> partidos = sistema.getListaPartidos().stream()
                    .filter(p -> p.getIdTorneo() == idTorneo)
                    .collect(Collectors.toList());

            if (partidos.isEmpty()) {
                vistaUsuario.mensaje(
                        "No hay partidos cargados para el torneo " + idTorneo + "."
                );
                return;
            }

            vistaUsuario.mensaje(
                    "=== PARTIDOS PROGRAMADOS (Torneo " + idTorneo + ") ==="
            );

            // recorrer y mostrar cada partido
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

                vistaUsuario.mensaje(
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
            vistaUsuario.mensaje("Error al consultar partidos: " + e.getMessage());
        }
    }

    // mismo metodo para ver clasificacion 
    public void verClasificacion() {
        try {
            // datos de torneo y grupo
            int idTorneo = Integer.parseInt(
                    vistaUsuario.pedirDato("Ingrese ID del torneo: ")
            );
            String idGrupo = vistaUsuario.pedirDato(
                    "Ingrese el ID del grupo (ej: A): "
            );

            EstadisticaDAO estadisticaDAO = new EstadisticaDAO(sistema.getConexion());
            ParejaDAO parejaDAO = sistema.getParejaDAO();

            // estadisticas del grupo y torneo
            List<Estadistica> ranking = estadisticaDAO
                    .obtenerEstadisticasPorGrupoYTorneo(idGrupo, idTorneo);

            if (ranking.isEmpty()) {
                vistaUsuario.mensaje(
                        "No hay estadisticas para el torneo " + idTorneo
                        + ", grupo " + idGrupo + "."
                );
                return;
            }

            vistaUsuario.mensaje(
                    "=== CLASIFICACION Torneo " + idTorneo + " - Grupo " + idGrupo + " ==="
            );

            // recorremos y mostramos cada pareja con su estadística
            for (Estadistica e : ranking) {
                Pareja pareja = parejaDAO.buscarParejaPorId(e.getIdPareja());
                String n1 = pareja.getJugador1().getNombre();
                String n2 = pareja.getJugador2().getNombre();
                vistaUsuario.mensaje(
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
            vistaUsuario.mensaje("Error al mostrar clasificacion: " + e.getMessage());
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
            int idTorneo = Integer.parseInt(vistaUsuario.pedirDato("Ingrese el ID del torneo: "));
            Pareja campeona = obtenerParejaGanadora(idTorneo);

            if (campeona != null) {
                Estadistica estadistica = sistema.getEstadisticaDAO().buscarEstadisticaPorIdPareja(campeona.getIdPareja());

                if (estadistica != null) {
                    vistaUsuario.mensaje("");
                    vistaUsuario.mensaje("Criterios para desempate:");
                    vistaUsuario.mensaje("1) Mas partidos ganados.");
                    vistaUsuario.mensaje("2) Mayor diferencia entre ganados y perdidos.");
                    vistaUsuario.mensaje("3) Menor cantidad de partidos jugados.\n");
                    
                    vistaUsuario.mensaje("");
                    vistaUsuario.mensaje("La PAREJA CAMPEONA es: "
                            + campeona.getJugador1().getNombre() + " y "
                            + campeona.getJugador2().getNombre());
                    
                    vistaUsuario.mensaje("");
                    vistaUsuario.mensaje("Estadisticas:");
                    vistaUsuario.mensaje("a. Partidos jugados: " + estadistica.getPartidosJugados());
                    vistaUsuario.mensaje("b. Partidos ganados: " + estadistica.getPartidosGanados());
                    vistaUsuario.mensaje("c. Partidos perdidos: " + estadistica.getPartidosPerdidos());
                } else {
                    vistaUsuario.mensaje("No se encontraron estadisticas para la pareja ganadora.");
                }

            } else {
                vistaUsuario.mensaje("No se pudo determinar una pareja ganadora.");
            }

        } catch (SQLException e) {
            vistaUsuario.mensaje("Error al obtener la pareja ganadora: " + e.getMessage());
        }
    }
}
