package com.mycompany.sistemadegestionpadeltpi.Modelos;

import java.util.*;

public class Partido {

    private int idPartido;
    private Pareja pareja1;
    private Pareja pareja2;
    private String resultado;
    private String idGrupo;

    public Partido(int idPartido, Pareja pareja1, Pareja pareja2, String resultado, String idGrupo) {
        this.idPartido = idPartido;
        this.pareja1 = pareja1;
        this.pareja2 = pareja2;
        this.resultado = resultado;
        this.idGrupo = idGrupo;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }
    

    public Partido() {
    }

    public Pareja getPareja1() {
        return pareja1;
    }

    public void setPareja1(Pareja pareja1) {
        this.pareja1 = pareja1;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public Pareja getPareja2() {
        return pareja2;
    }

    public void setPareja2(Pareja pareja2) {
        this.pareja2 = pareja2;
    }
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }


}
