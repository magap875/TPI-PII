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
                    consultarPartidosPropios();
                case 4 ->
                    consultarPartidosDelTorneo();
                case 5 ->
                    verClasificacion();
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

    // metodo para inscribirse a un torneo, que incluye el registro de la pareja
    public void inscribirseATorneo() {
        vistaJugador.mensaje("*** Registre la pareja ***");

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
                Pareja pareja = new Pareja(idPareja, jugador1, jugador2,idTorneo, idGrupoAleatorio);

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
    
    
    // metodo propio para ver sus partidos
    public void consultarPartidosPropios() {
    try {
        int idJugador = Integer.parseInt(vistaJugador.pedirDato("Ingrese su ID para consultar sus partidos: "));
        List<Partido> partidos = sistema.getListaPartidos();

        List<Partido> partidosDelJugador = partidos.stream()
                .filter(p -> participaJugadorEnPartido(p, idJugador))
                .collect(Collectors.toList());

        if (partidosDelJugador.isEmpty()) {
            vistaJugador.mensaje("No hay partidos registrados para este jugador.");
            return;
        }

        vistaJugador.mensaje("=== PARTIDOS DEL JUGADOR ===");
        for (Partido partido : partidosDelJugador) {
            int idPareja1 = partido.getPareja1().getIdPareja();
            String jugador1Pareja1 = partido.getPareja1().getJugador1().getNombre();
            String jugador2Pareja1 = partido.getPareja1().getJugador2().getNombre();

            int idPareja2 = partido.getPareja2().getIdPareja();
            String jugador1Pareja2 = partido.getPareja2().getJugador1().getNombre();
            String jugador2Pareja2 = partido.getPareja2().getJugador2().getNombre();

            String resultado = partido.getResultado().isEmpty() ? "Pendiente" : partido.getResultado();

            vistaJugador.mensaje(
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
        vistaJugador.mensaje("Error al consultar partidos: " + e.getMessage());
    }
}

    
    
    
    // metodo para consultar todos los partidos 
    public void consultarPartidosDelTorneo() {
    try {
        List<Partido> partidos = sistema.getListaPartidos();

        if (partidos.isEmpty()) {
            vistaJugador.mensaje("No hay partidos cargados.");
            return;
        }

        vistaJugador.mensaje("=== PARTIDOS PROGRAMADOS ===");

        for (Partido partido : partidos) {
            int idPareja1 = partido.getPareja1().getIdPareja();
            String jugador1Pareja1 = partido.getPareja1().getJugador1().getNombre();
            String jugador2Pareja1 = partido.getPareja1().getJugador2().getNombre();

            int idPareja2 = partido.getPareja2().getIdPareja();
            String jugador1Pareja2 = partido.getPareja2().getJugador1().getNombre();
            String jugador2Pareja2 = partido.getPareja2().getJugador2().getNombre();

            String resultado = partido.getResultado().isEmpty() ? "Pendiente" : partido.getResultado();

            vistaJugador.mensaje(
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
        vistaJugador.mensaje("Error al consultar partidos: " + e.getMessage());
    }
}

    
    
    // consultar clasificacion por grupo
    public void verClasificacion() {
        try {
            String idGrupo = vistaJugador.pedirDato("Ingrese el ID del grupo (ej: A): ");
            EstadisticaDAO estadisticaDAO = new EstadisticaDAO(sistema.getConexion());
            ParejaDAO parejaDAO = sistema.getParejaDAO();

            List<Estadistica> ranking = estadisticaDAO.obtenerEstadisticasOrdenadasPorGrupo(idGrupo);

            if (ranking.isEmpty()) {
                vistaJugador.mensaje("No hay estadisticas cargadas para el grupo " + idGrupo);
                return;
            }

            vistaJugador.mensaje("=== CLASIFICACION GRUPO " + idGrupo + " ===");

            for (Estadistica e : ranking) {
                Pareja pareja = parejaDAO.buscarParejaPorId(e.getIdPareja());
                String nombre1 = pareja.getJugador1().getNombre();
                String nombre2 = pareja.getJugador2().getNombre();
                vistaJugador.mensaje(
                        String.format("Pareja %d (%s y %s): PJ: %d | PG: %d | PP: %d",
                                e.getIdPareja(), nombre1, nombre2,
                                e.getPartidosJugados(), e.getPartidosGanados(), e.getPartidosPerdidos())
                );
            }

        } catch (Exception e) {
            vistaJugador.mensaje("Error al mostrar clasificacion: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    // metodo auxiliar
    public boolean participaJugadorEnPartido(Partido partido, int idJugador) {
        return partido.getPareja1().getJugador1().getId() == idJugador
                || partido.getPareja1().getJugador2().getId() == idJugador
                || partido.getPareja2().getJugador1().getId() == idJugador
                || partido.getPareja2().getJugador2().getId() == idJugador;
    }

}
