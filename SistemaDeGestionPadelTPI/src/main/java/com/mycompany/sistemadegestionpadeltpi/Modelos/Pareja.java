package com.mycompany.sistemadegestionpadeltpi.Modelos;
public class Pareja {
 private int idPareja;
 private Jugador jugador1;
 private Jugador jugador2;
 private String categoria;

    public Pareja(int idPareja, Jugador jugador1, Jugador jugador2, String categoria) {
        this.idPareja = idPareja;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.categoria = categoria;
    }

    public int getIdPareja() {
        return idPareja;
    }

    public void setIdPareja(int idPareja) {
        this.idPareja = idPareja;
    }

    

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
 
}
