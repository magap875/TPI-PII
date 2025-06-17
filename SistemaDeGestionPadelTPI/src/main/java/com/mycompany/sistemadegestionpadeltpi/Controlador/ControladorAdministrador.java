package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Vista.VistaAdministrador;


public class ControladorAdministrador {
    private VistaAdministrador vista = new VistaAdministrador();
    
    public void ejecutarMenuAdministrador() {
        int opcion = 0;
        do {
            opcion = vista.mostrarMenuAdministrador();
            switch (opcion) {
                case 1 ->
                    consultarPartidos();
                case 2 ->
                    verResultados();
                case 3 ->
                    verClasificacion();
                case 4 ->
                    crearTorneo();
                case 5 ->
                    cargarResultado();

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
    
    public void crearTorneo(){
        
    }
    
    public void cargarResultado(){
        
    }
}
