package com.mycompany.sistemadegestionpadeltpi.Modelos;
import java.util.*;

public class Torneo {
 private String nombre;
 private String categoria;
 private String estado;
 private List<Pareja> parejasInscriptas=new ArrayList<>();
 private List<Grupo> fixture=new ArrayList<>();
 private List<Partido> playoffs=new ArrayList<>();
 private int capacidadMaxima;

    public Torneo(String nombre, String categoria, String estado, int capacidadMaxima) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.estado = estado;
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
    
    

}
