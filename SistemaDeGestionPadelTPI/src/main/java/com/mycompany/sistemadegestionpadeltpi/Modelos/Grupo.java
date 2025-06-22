package com.mycompany.sistemadegestionpadeltpi.Modelos;

public class Grupo {

    private String idGrupo;
    private int idTorneo;

    public Grupo(String idGrupo, int idTorneo) {
        this.idGrupo = idGrupo;
        this.idTorneo = idTorneo;
    }
    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }
    
}
