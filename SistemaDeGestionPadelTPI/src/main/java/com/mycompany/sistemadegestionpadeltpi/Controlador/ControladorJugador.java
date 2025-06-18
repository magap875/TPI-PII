package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.DAO.JugadorDAO;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;
import java.sql.Connection;



public class ControladorJugador {
    private VistaJugador vista = new VistaJugador();
    private Connection conexion;
    public ControladorJugador(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void ejecutarMenuJugador() {
        int opcion;
        do {
            opcion = vista.mostrarMenuJugador();
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
            int id = Integer.parseInt(vista.pedirDato("Ingrese su id: "));
            String nombre = vista.pedirDato("Ingrese su nombre: ");
            String dni = vista.pedirDato("Ingrese su dni: ");
            String telefono = vista.pedirDato("Ingrese su telefono: ");

            Jugador jugador = new Jugador(id, nombre, dni, telefono);
            JugadorDAO dao = new JugadorDAO(conexion);
            dao.insertarJugador(jugador);

            vista.mensaje("Registro exitoso.");
        } catch (Exception e) {
            vista.mensaje("Error al registrar: " + e.getMessage());
        }
    }
    
    
    public void consultarPartidos(){
    
    }
    
    public void verResultados(){
    
    }
    
    public void verClasificacion(){
    
    }
    
    public void inscribirseATorneo(){
    
    }
    
}
