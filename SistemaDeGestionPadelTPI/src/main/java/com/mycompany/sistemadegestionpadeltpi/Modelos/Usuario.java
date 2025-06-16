package com.mycompany.sistemadegestionpadeltpi.Modelos;


public abstract class Usuario {
 protected int Id; 
 protected String nombre;

    public Usuario(int Id, String nombre) {
        this.Id = Id;
        this.nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
  
          
}
