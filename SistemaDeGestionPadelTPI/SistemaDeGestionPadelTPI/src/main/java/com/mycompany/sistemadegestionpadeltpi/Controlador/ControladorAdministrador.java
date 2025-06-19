package com.mycompany.sistemadegestionpadeltpi.Controlador;
import com.mycompany.sistemadegestionpadeltpi.Modelos.Torneo;
import com.mycompany.sistemadegestionpadeltpi.Vista.VistaAdministrador;
public class ControladorAdministrador {
    private VistaAdministrador vistaAdministrador = new VistaAdministrador();
    public void ejecutarMenuAdministrador() {
        int opcion = 0;
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
                case 5 ->
                    cargarResultado();

            }
        } while (opcion != 0);
    }

    public void consultarPartidos() {

    }

    public void verResultados() {

    }

    public void verClasificacion() {

    }
    public void crearTorneo() {
        String nombre=vistaAdministrador.pedirDato("Nombre del torneo: ");
        String categoria=vistaAdministrador.pedirDato("Categoria del torneo: ");
        int cuposDisponibles=Integer.parseInt(vistaAdministrador.pedirDato("Cantidad de parejas del torneo: "));
        Torneo torneo=new Torneo(nombre,categoria,cuposDisponibles);
        
    }

    public void cargarResultado() {       
    }
}