package com.mycompany.sistemadegestionpadeltpi.Modelos;
import java.util.*;
public class Partido {
  private Pareja pareja1;
  private Pareja pareja2;
  private Date fecha;
  private Resultado resultado;
  private String fase;

    public Partido(Pareja pareja1, Pareja pareja2, Date fecha, Resultado resultado, String fase) {
        this.pareja1 = pareja1;
        this.pareja2 = pareja2;
        this.fecha = fecha;
        this.resultado = resultado;
        this.fase = fase;
    }

    public Pareja getPareja1() {
        return pareja1;
    }

    public void setPareja1(Pareja pareja1) {
        this.pareja1 = pareja1;
    }

    public Pareja getPareja2() {
        return pareja2;
    }

    public void setPareja2(Pareja pareja2) {
        this.pareja2 = pareja2;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }
  
}
