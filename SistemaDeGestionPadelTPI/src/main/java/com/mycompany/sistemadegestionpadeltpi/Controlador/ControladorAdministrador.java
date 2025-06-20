package com.mycompany.sistemadegestionpadeltpi.Controlador;
import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.TorneoDAO;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
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
                case 1 -> consultarPartidos();
                case 2 -> verResultados();
                case 3 -> verClasificacion();
                case 4 -> crearTorneo();
                case 5 -> cargarResultado();
            }
        } while (opcion != 0);
    }

    public void consultarPartidos() {}
    public void verResultados() {}
    public void verClasificacion() {}
    public void cargarResultado() {}

    public void crearTorneo() {
        try {
            String nombre = vistaAdministrador.pedirDato("Nombre del torneo: ");
            String categoria = vistaAdministrador.pedirDato("Categoría del torneo: ");
            int cuposDisponibles = Integer.parseInt(vistaAdministrador.pedirDato("Cantidad de parejas del torneo: "));

            Torneo torneo = new Torneo(nombre, categoria, cuposDisponibles, this.conexion); // suponiendo que este constructor existe

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
            vistaAdministrador.mensaje("Error: cupos disponibles debe ser un número.");
        } catch (SQLException e) {
            vistaAdministrador.mensaje("Error al insertar en la base de datos: " + e.getMessage());
        }
    }
}

