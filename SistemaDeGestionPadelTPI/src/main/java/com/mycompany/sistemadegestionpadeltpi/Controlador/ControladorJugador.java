package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.DAO.ParejaDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Pareja;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorJugador {

    private VistaJugador vistaJugador = new VistaJugador();
    private VistaGeneral vistaGeneral = new VistaGeneral();
    private Connection conexion;
    private List<Jugador> jugadores;
    private JugadorDAO jugadorDAO;
    private Scanner s = new Scanner(System.in);
    private ParejaDAO parejaDAO;

    public ControladorJugador(Connection conexion) {
    this.conexion = conexion;
    this.jugadorDAO = new JugadorDAO(conexion);
    this.parejaDAO = new ParejaDAO(conexion);
    this.vistaJugador = new VistaJugador();
    this.jugadores = new ArrayList<>();
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

    public void registrarJugador() {
        try {
            int id = Integer.parseInt(vistaJugador.pedirDato("Ingrese su id: "));
            String nombre = vistaJugador.pedirDato("Ingrese su nombre: ");
            String dni = vistaJugador.pedirDato("Ingrese su dni: ");
            String telefono = vistaJugador.pedirDato("Ingrese su telefono: ");

            Jugador jugador = new Jugador(id, nombre, dni, telefono);
            JugadorDAO dao = new JugadorDAO(conexion);
            dao.insertarJugador(jugador);
            vistaJugador.mensaje("Registro exitoso.");

        } catch (Exception e) {
            vistaJugador.mensaje("Error al registrar: " + e.getMessage());
        }
    }

    public void consultarPartidos() {
        String nombre = vistaJugador.pedirDato("Ingrese el NOMBRE del jugador a consultar: ");
        buscarJugadorPorNombre(nombre);
        for (Pareja p : mostrarTodasLasParejas()){
            if (nombre.equalsIgnoreCase(p.getNombreJugador1()) || (nombre.equalsIgnoreCase(p.getNombreJugador2()))) { 
                
            }
        }
    }


    public void verResultados() {

    }

    public void verClasificacion() {

    }

    public void inscribirseATorneo() {

    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        try {
            Jugador jugador = jugadorDAO.buscarPorNombre(nombre);
            if (jugador == null) {
                vistaJugador.mensaje("Jugador no encontrado.");
            }
            return jugador;
        } catch (SQLException e) {
            vistaJugador.mensaje("Error al buscar jugador: " + e.getMessage());
            return null; 
        }
    }

    public List<Pareja> mostrarTodasLasParejas() {
        try {
            List<Pareja> parejas = parejaDAO.obtenerTodas();
            if (parejas.isEmpty()) {
                vistaGeneral.mensaje("No hay parejas registradas.");
            }
            return parejas; 
        } catch (SQLException e) {
            vistaGeneral.mensaje("Error al mostrar parejas: " + e.getMessage());
            return null;
        }
    }

}


