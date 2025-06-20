package com.mycompany.sistemadegestionpadeltpi.Modelos;
import com.mycompany.sistemadegestionpadeltpi.Main.SistemaDeGestionPadelTPI;
import java.sql.Connection;
import java.util.*;

public class Torneo {
    private String nombre;
    private String categoria;
    private List<Pareja> parejasInscriptas;
    private List<Grupo> fixture;
    private Connection conexion;
    private List<Partido> playoffs;
    private int capacidadMaxima;
    private SistemaDeGestionPadelTPI sistema = new SistemaDeGestionPadelTPI(conexion);
   public Torneo(String nombre, String categoria, int capacidadMaxima, Connection conexion) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.capacidadMaxima = capacidadMaxima;
    this.conexion = conexion;

    this.sistema = new SistemaDeGestionPadelTPI(conexion); // ahora sí
    sistema.traerJugadoresDesdeBD();
    this.parejasInscriptas=new ArrayList<>();
    this.fixture = new ArrayList<>();
    this.playoffs = new ArrayList<>();
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
