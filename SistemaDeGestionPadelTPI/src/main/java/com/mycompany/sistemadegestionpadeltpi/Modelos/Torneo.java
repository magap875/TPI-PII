package com.mycompany.sistemadegestionpadeltpi.Modelos;
import java.util.*;

public class Torneo {
 private String nombre;
 private String Categoria;
 private String estado;
 private List<Pareja> parejasInscriptas=new ArrayList<>();
 private List<Grupo> fixture=new ArrayList<>();
 private List<Partido> playoffs=new ArrayList<>();

    public Torneo(String nombre, String Categoria, String estado) {
        this.nombre = nombre;
        this.Categoria = Categoria;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Pareja> getParejasInscriptas() {
        return parejasInscriptas;
    }

    public void setParejasInscriptas(List<Pareja> parejasInscriptas) {
        this.parejasInscriptas = parejasInscriptas;
    }

    public List<Grupo> getFixture() {
        return fixture;
    }

    public void setFixture(List<Grupo> fixture) {
        this.fixture = fixture;
    }

    public List<Partido> getPlayoffs() {
        return playoffs;
    }

    public void setPlayoffs(List<Partido> playoffs) {
        this.playoffs = playoffs;
    }
 

}
