package com.mycompany.sistemadegestionpadeltpi.Modelos;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre) {
        super(id, nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
