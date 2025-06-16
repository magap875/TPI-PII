package com.mycompany.sistemadegestionpadeltpi.Modelos;
import java.util.*;


public class Resultado {
  private List<Integer> setsPareja1=new ArrayList<>();
  private List<Integer> setsPareja2=new ArrayList<>();

    public Resultado() {
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
