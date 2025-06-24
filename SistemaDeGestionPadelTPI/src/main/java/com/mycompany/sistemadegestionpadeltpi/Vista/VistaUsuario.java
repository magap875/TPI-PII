package com.mycompany.sistemadegestionpadeltpi.Vista;

import java.util.Scanner;

public class VistaUsuario {

    private Scanner s = new Scanner(System.in);

    public int mostrarMenuUsuario() {
        int opcion = 0;
        System.out.println();
        System.out.println("***MENU USUARIO***");
        System.out.println("1. Consultar partidos del torneo.");
        System.out.println("2. Ver clasificacion.");
        System.out.println("3. Ver pareja ganadora del torneo.");
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
