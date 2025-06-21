package com.mycompany.sistemadegestionpadeltpi.Vista;

import java.util.Scanner;

public class VistaAdministrador {

    private Scanner s = new Scanner(System.in);

    public int mostrarMenuAdministrador() {
        int opcion = 0;
        System.out.println();
        System.out.println("***MENU ADMINISTRADOR***");
        System.out.println("1. Consultar partidos.");
        System.out.println("2. Ver resultados.");
        System.out.println("3. Ver clasificacion.");
        System.out.println("4. Crear torneo.");
        System.out.println("5. Cargar resultado.");
        System.out.println("6. Generar partidos por grupo.");
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
