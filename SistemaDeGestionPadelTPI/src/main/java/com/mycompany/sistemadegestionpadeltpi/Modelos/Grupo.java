package com.mycompany.sistemadegestionpadeltpi.Modelos;

import java.util.*;

public class Grupo {

    private String idGrupo;
    private List<Pareja> parejas = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();

    public Grupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<Pareja> getParejas() {
        return parejas;
    }

    public void setParejas(List<Pareja> parejas) {
        this.parejas = parejas;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

}
