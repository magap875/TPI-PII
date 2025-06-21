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

public class ControladorAdministrador {

    private final VistaAdministrador vistaAdministrador = new VistaAdministrador();
    private final SistemaDeGestionPadelTPI sistema;
    private Connection conexion;

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
                    consultarPartidos();
                case 2 ->
                    verResultados();
                case 3 ->
                    verClasificacion();
                case 4 ->
                    crearTorneo();
                case 6 ->
                    generarPartidosPorGrupo();
                case 5 ->
                    cargarResultado();
            }
        } while (opcion != 0);
    }

    public void consultarPartidos() {
    }

    public void verResultados() {
    }

    public void cargarResultado() {
        try {
            int idPartido = Integer.parseInt(vistaAdministrador.pedirDato("Ingrese ID del partido: "));
            String resultado = vistaAdministrador.pedirDato("Ingrese resultado (ej: 6-3, 6-4): ");

            // actualizamos resultado en partido
            sistema.getPartidoDAO().actualizarResultado(idPartido, resultado);

            // obtenemos el partido para saber que parejas jugaron
            Partido partido = sistema.getPartidoDAO().buscarPartidoPorId(idPartido);
            if (partido == null) {
                vistaAdministrador.mensaje("No se encontró el partido con ese ID.");
                return;
            }

            // obtenemos ganador
            int ganador = Integer.parseInt(vistaAdministrador.pedirDato(
                    "Ingrese ID de la pareja ganadora (" + partido.getPareja1().getIdPareja() + " o " + partido.getPareja2().getIdPareja() + "): "
            ));

            int perdedor;
            if (ganador == partido.getPareja1().getIdPareja()) {
                perdedor = partido.getPareja2().getIdPareja();
            } else if (ganador == partido.getPareja2().getIdPareja()) {
                perdedor = partido.getPareja1().getIdPareja();
            } else {
                vistaAdministrador.mensaje("ID de pareja ganadora inválido.");
                return;
            }

            // actualizamos estadisticas
            sistema.getEstadisticaDAO().actualizarEstadisticas(ganador, perdedor);

            vistaAdministrador.mensaje("Resultado y estadísticas actualizadas correctamente.");

        } catch (Exception e) {
            vistaAdministrador.mensaje("Error al cargar resultado: " + e.getMessage());
        }
    }

    // metodo de creacion de torneo y grupos
    public void crearTorneo() {
        try {
            String nombre = vistaAdministrador.pedirDato("Nombre del torneo: ");
            String categoria = vistaAdministrador.pedirDato("Categoria del torneo: ");
            int cuposDisponibles = Integer.parseInt(vistaAdministrador.pedirDato("Cantidad de parejas del torneo: "));

            Torneo torneo = new Torneo(nombre, categoria, cuposDisponibles, this.conexion);

            TorneoDAO torneoDAO = new TorneoDAO(conexion);
            torneoDAO.insertarTorneo(torneo);

            int cantidadGrupos = cuposDisponibles / 3;
            GrupoDAO grupoDAO = new GrupoDAO(conexion);
            List<Grupo> grupos = new ArrayList<>();

            for (int i = 0; i < cantidadGrupos; i++) {
                char idGrupo = (char) ('A' + i);
                Grupo grupo = new Grupo(String.valueOf(idGrupo));
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

    // metodo para generar los partidos por grupo
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

                    if (p1.getIdGrupo().equals(p2.getIdGrupo())) {
                        Partido partido = new Partido(0, p1, p2, "", p1.getIdGrupo());
                        partidoDAO.insertarPartido(partido);
                    }
                }
            }

            sistema.traerPartidosDesdeBD();
            vistaAdministrador.mensaje("Partidos generados correctamente.");

        } catch (Exception e) {
            vistaAdministrador.mensaje("Error al generar partidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // consultar clasificacion por grupo
    public void verClasificacion() {
        try {
            String idGrupo = vistaAdministrador.pedirDato("Ingrese el ID del grupo (ej: A): ");
            EstadisticaDAO estadisticaDAO = new EstadisticaDAO(sistema.getConexion());
            ParejaDAO parejaDAO = sistema.getParejaDAO();

            List<Estadistica> ranking = estadisticaDAO.obtenerEstadisticasOrdenadasPorGrupo(idGrupo);

            if (ranking.isEmpty()) {
                vistaAdministrador.mensaje("No hay estadisticas cargadas para el grupo " + idGrupo);
                return;
            }

            vistaAdministrador.mensaje("=== CLASIFICACION GRUPO " + idGrupo + " ===");

            for (Estadistica e : ranking) {
                Pareja pareja = parejaDAO.buscarParejaPorId(e.getIdPareja());
                String nombre1 = pareja.getJugador1().getNombre();
                String nombre2 = pareja.getJugador2().getNombre();
                vistaAdministrador.mensaje(
                        String.format("Pareja %d (%s y %s): PJ: %d | PG: %d | PP: %d",
                                e.getIdPareja(), nombre1, nombre2,
                                e.getPartidosJugados(), e.getPartidosGanados(), e.getPartidosPerdidos())
                );
            }

        } catch (Exception e) {
            vistaAdministrador.mensaje("Error al mostrar clasificacion: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
