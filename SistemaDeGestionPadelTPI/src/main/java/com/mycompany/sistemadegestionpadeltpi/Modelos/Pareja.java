package com.mycompany.sistemadegestionpadeltpi.Modelos;

public class Pareja {

    private int idPareja;
    private String nombreJugador1;
    private String nombreJugador2;

    public Pareja(int idPareja, String nombreJugador1, String nombreJugador2) {
        this.idPareja = idPareja;
        this.nombreJugador1 = nombreJugador1;
        this.nombreJugador2 = nombreJugador2;
    }

    public Pareja() {
    }
    
    public int getIdPareja() {
        return idPareja;
    }

    public void setIdPareja(int idPareja) {
        this.idPareja = idPareja;
    }

    public String getNombreJugador1() {
        return nombreJugador1;
    }

    public void setNombreJugador1(String nombreJugador1) {
        this.nombreJugador1 = nombreJugador1;
    }

    public String getNombreJugador2() {
        return nombreJugador2;
    }

    public void setNombreJugador2(String nombreJugador2) {
        this.nombreJugador2 = nombreJugador2;
    }

}
