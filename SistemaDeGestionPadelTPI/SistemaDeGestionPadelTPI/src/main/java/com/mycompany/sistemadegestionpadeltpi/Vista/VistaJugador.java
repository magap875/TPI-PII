package com.mycompany.sistemadegestionpadeltpi.Vista;
import java.util.Scanner;
public class VistaJugador {
    private Scanner s = new Scanner(System.in);
    
    public int mostrarMenuJugador() {
        int opcion;
        System.out.println();
        System.out.println("***MENU JUGADOR***");
        System.out.println("1. Registrarse.");
        System.out.println("2. Consultar partido.");
        System.out.println("3. Ver resultado.");
        System.out.println("4. Ver clasificacion.");
        System.out.println("5. Inscribirse a torneo.");
        System.out.println("0. Salir.");
        System.out.print("Ingrese su opcion: ");
        opcion = Integer.parseInt(s.nextLine());
        System.out.println();
        return opcion;
    }
    
    public void mensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public String pedirDato(String mensaje) {
        System.out.print(mensaje);
        return s.nextLine();
    }
}
