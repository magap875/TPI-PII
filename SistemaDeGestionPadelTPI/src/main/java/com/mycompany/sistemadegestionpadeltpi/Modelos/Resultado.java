package com.mycompany.sistemadegestionpadeltpi.Modelos;
import java.util.*;


public class Resultado {
  private int idResultado;
  private List<Integer> setsPareja1;
  private List<Integer> setsPareja2;

    public Resultado() {
    }

    public Resultado(int idResultado, List<Integer> setsPareja1, List<Integer> setsPareja2) {
        this.idResultado = idResultado;
        this.setsPareja1 = setsPareja1;
        this.setsPareja2 = setsPareja2;
    }

    public int getIdResultado() {
        return idResultado;
    }
    public List<Integer> getSetsPareja1() {
        return setsPareja1;
    }

    public void setSetsPareja1(List<Integer> setsPareja1) {
        this.setsPareja1 = setsPareja1;
    }

    public List<Integer> getSetsPareja2() {
        return setsPareja2;
    }

    public void setSetsPareja2(List<Integer> setsPareja2) {
        this.setsPareja2 = setsPareja2;
    }
  
}
