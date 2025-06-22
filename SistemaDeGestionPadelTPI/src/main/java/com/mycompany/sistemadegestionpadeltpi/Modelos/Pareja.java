package com.mycompany.sistemadegestionpadeltpi.Modelos;

import com.mycompany.sistemadegestionpadeltpi.Modelos.Jugador;

public class Pareja {

    private int idPareja;
    private Jugador jugador1;
    private Jugador jugador2;
    private int idTorneo;
    private String idGrupo;

    public Pareja(int idPareja, Jugador jugador1, Jugador jugador2, int idTorneo, String idGrupo) {
        this.idPareja = idPareja;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.idTorneo = idTorneo;
        this.idGrupo = idGrupo;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Pareja() {
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

    @Override
    public String toString() {
        return "Pareja{"
                + "idPareja=" + idPareja
                + ", jugador1='" + jugador1.getNombre() + '\''
                + ", jugador2='" + jugador2.getNombre() + '\''
                + ", idGrupo='" + idGrupo + '\''
                + '}';
    }

}
