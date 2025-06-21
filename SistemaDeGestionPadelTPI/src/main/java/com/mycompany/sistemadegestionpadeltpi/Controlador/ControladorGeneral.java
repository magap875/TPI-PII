package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;

public class ControladorGeneral {

    private VistaGeneral vistaGeneral = new VistaGeneral();
    private ControladorJugador controladorJugador;
    private ControladorUsuario controladorUsuario;
    private ControladorAdministrador controladorAdministrador;
    private SistemaDeGestionPadelTPI sistema;

    public ControladorGeneral(SistemaDeGestionPadelTPI sistema) {
        this.sistema = sistema;
        this.controladorJugador = new ControladorJugador(sistema);
        //this.controladorUsuario = new ControladorUsuario(conexion);
        this.controladorAdministrador = new ControladorAdministrador(sistema);
    }

    public void ejecutarMenuGeneral() {
        int opcion;
        do {
            opcion = vistaGeneral.mostrarMenuGeneral();
            switch (opcion) {
                case 1 ->
                    controladorUsuario.ejecutarMenuUsuario();
                case 2 ->
                    controladorJugador.ejecutarMenuJugador();
                case 3 ->
                    controladorAdministrador.ejecutarMenuAdministrador();
                case 0 ->
                    vistaGeneral.mensaje("Saliendo del sistema...");

            }
        } while (opcion != 0);
    }
}
