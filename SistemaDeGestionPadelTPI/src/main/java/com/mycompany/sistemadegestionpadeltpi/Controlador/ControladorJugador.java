package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Vista.VistaJugador;



public class ControladorJugador {
    private VistaJugador vista = new VistaJugador();
    
    public void ejecutarMenuJugador() {
        int opcion = 0;
        do {
            opcion = vista.mostrarMenuJugador();
            switch (opcion) {
                case 1 ->
                    consultarPartidos();
                case 2 ->
                    verResultados();
                case 3 ->
                    verClasificacion();
                case 4 ->
                    inscribirseATorneo();

            }
        } while (opcion != 0);
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
