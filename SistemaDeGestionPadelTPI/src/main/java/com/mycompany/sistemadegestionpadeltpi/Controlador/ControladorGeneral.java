package com.mycompany.sistemadegestionpadeltpi.Controlador;

import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaGeneral;

public class ControladorGeneral {

    private final VistaGeneral vistaGeneral = new VistaGeneral();
    private final ControladorJugador controladorJugador;
    private ControladorUsuario controladorUsuario;
    private final ControladorAdministrador controladorAdministrador;

    public ControladorGeneral(SistemaDeGestionPadelTPI sistema) {
        this.controladorJugador = new ControladorJugador(sistema);
        this.controladorUsuario = new ControladorUsuario(sistema);
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
