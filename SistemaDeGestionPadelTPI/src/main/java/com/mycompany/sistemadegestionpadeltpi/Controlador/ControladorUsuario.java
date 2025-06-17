package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Vista.VistaUsuario;

public class ControladorUsuario {
    private VistaUsuario vista = new VistaUsuario();

    public void ejecutarMenuUsuario() {
        int opcion = 0;
        do {
            opcion = vista.mostrarMenuUsuario();
            switch (opcion) {
                case 1 ->
                    consultarPartidos();
                case 2 ->
                    verResultados();
                case 3 ->
                    verClasificacion();

            }
        } while (opcion != 0);
    }
      
    public void consultarPartidos(){
    
    }
    
    public void verResultados(){
    
    }
    
    public void verClasificacion(){
    
    }
    
}
