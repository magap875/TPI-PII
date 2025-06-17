package com.mycompany.sistemadegestionpadeltpi.Vista;

import java.util.Scanner;

public class VistaGeneral {
    private Scanner s = new Scanner(System.in);

    public int mostrarMenuGeneral() {
        int opcion = 0;
        System.out.println();
        System.out.println("||| SISTEMA DE GESTION DE PADEL |||");
        System.out.println("1. Ingrese como usuario.");
        System.out.println("2. Ingrese como jugador.");
        System.out.println("3. Ingrese como administrador.");
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
