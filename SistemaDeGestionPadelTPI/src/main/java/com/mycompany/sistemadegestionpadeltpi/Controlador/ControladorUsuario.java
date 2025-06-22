package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Vista.VistaUsuario;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Estadistica;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Partido;
import java.util.List;

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

            }
        } while (opcion != 0);
    }

    
    // mismo metodo para consultar partidos generales
    public void consultarPartidosDelTorneo() {
    try {
        List<Partido> partidos = sistema.getListaPartidos();

        if (partidos.isEmpty()) {
            vistaUsuario.mensaje("No hay partidos cargados.");
            return;
        }

        vistaUsuario.mensaje("=== PARTIDOS PROGRAMADOS ===");

        for (Partido partido : partidos) {
            int idPareja1 = partido.getPareja1().getIdPareja();
            String jugador1Pareja1 = partido.getPareja1().getJugador1().getNombre();
            String jugador2Pareja1 = partido.getPareja1().getJugador2().getNombre();

            int idPareja2 = partido.getPareja2().getIdPareja();
            String jugador1Pareja2 = partido.getPareja2().getJugador1().getNombre();
            String jugador2Pareja2 = partido.getPareja2().getJugador2().getNombre();

            String resultado = partido.getResultado().isEmpty() ? "Pendiente" : partido.getResultado();

            vistaUsuario.mensaje(
                String.format(
                    "Partido ID: %d | Pareja %d (%s y %s) | Pareja %d (%s y %s) | Grupo: %s | Resultado: %s",
                    partido.getIdPartido(),
                    idPareja1, jugador1Pareja1, jugador2Pareja1,
                    idPareja2, jugador1Pareja2, jugador2Pareja2,
                    partido.getIdGrupo(),
                    resultado
                )
            );
        }
    } catch (Exception e) {
        vistaUsuario.mensaje("Error al consultar partidos: " + e.getMessage());
    }
}



    // mismo metodo para ver clasificacion general
    public void verClasificacion() {
        try {
            String idGrupo = vistaUsuario.pedirDato("Ingrese el ID del grupo (ej: A): ");
            List<Estadistica> ranking = sistema.getEstadisticaDAO().obtenerEstadisticasOrdenadasPorGrupo(idGrupo);

            if (ranking.isEmpty()) {
                vistaUsuario.mensaje("No hay estadísticas cargadas para el grupo " + idGrupo);
                return;
            }

            vistaUsuario.mensaje("=== CLASIFICACIÓN GRUPO " + idGrupo + " ===");

            for (Estadistica e : ranking) {
                Pareja pareja = sistema.getParejaDAO().buscarParejaPorId(e.getIdPareja());
                vistaUsuario.mensaje(
                        String.format("Pareja %d (%s y %s): PJ: %d | PG: %d | PP: %d",
                                e.getIdPareja(),
                                pareja.getJugador1().getNombre(),
                                pareja.getJugador2().getNombre(),
                                e.getPartidosJugados(), e.getPartidosGanados(), e.getPartidosPerdidos())
                );
            }

        } catch (Exception e) {
            vistaUsuario.mensaje("Error al mostrar clasificación: " + e.getMessage());
        }
    }

}
