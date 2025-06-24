package com.mycompany.sistemadegestionpadeltpi.Vista;

import java.util.Scanner;

public class VistaAdministrador {

    private final Scanner s = new Scanner(System.in);

    public int mostrarMenuAdministrador() {
        int opcion = 0;
        System.out.println();
        System.out.println("***MENU ADMINISTRADOR***");
        System.out.println("1. Crear torneo.");
        System.out.println("2. Generar partidos por grupo.");
        System.out.println("3. Cargar resultados.");
        System.out.println("4. Consultar partidos del torneo.");
        System.out.println("5. Ver clasificacion.");
        System.out.println("6. Ver pareja ganadora del torneo.");
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
