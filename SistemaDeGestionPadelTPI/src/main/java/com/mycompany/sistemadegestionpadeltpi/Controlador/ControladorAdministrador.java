package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.GrupoDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.TorneoDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Grupo;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Torneo;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaAdministrador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorAdministrador {

    private VistaAdministrador vistaAdministrador = new VistaAdministrador();
    private Connection conexion;
    private List<Pareja> parejas;
    private ParejaDAO parejaDAO;

    public ControladorAdministrador(Connection conexion) {
        this.conexion = conexion;
        this.parejaDAO = new ParejaDAO(conexion);
        this.parejas = parejaDAO.obtenerTodasLasParejas();
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

            if (cuposDisponibles % 3 != 0) {
                vistaAdministrador.mensaje("Error: La cantidad de parejas debe ser múltiplo de 3.");
                return;
            }

            Torneo torneo = new Torneo(nombre, categoria, cuposDisponibles);
            TorneoDAO torneoDAO = new TorneoDAO(conexion);
            torneoDAO.insertarTorneo(torneo);

            int cantidadGrupos = cuposDisponibles / 3;
            GrupoDAO grupoDAO = new GrupoDAO(conexion);
            List<Grupo> grupos = new ArrayList<>();

            for (int i = 0; i < cantidadGrupos; i++) {
                char idGrupo = (char) ('A' + i);
                Grupo grupo = new Grupo(String.valueOf(idGrupo), 0, 0, 0);
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


