package com.mycompany.sistemadegestionpadeltpi.Modelos;
public class Jugador extends Usuario {
private String dni;
private String telefono;
    public Jugador(int Id, String nombre, String dni, String telefono) {
        super(Id, nombre);
        this.dni = dni;
        this.telefono = telefono;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
