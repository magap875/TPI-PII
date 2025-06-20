package com.mycompany.sistemadegestionpadeltpi.Modelos;
public class Grupo {
    private String idGrupo;
    private Integer idPareja1;
    private Integer idPareja2;
    private Integer idPareja3;

    public Grupo(String idGrupo, Integer idPareja1, Integer idPareja2, Integer idPareja3) {
        this.idGrupo = idGrupo;
        this.idPareja1 = idPareja1;
        this.idPareja2 = idPareja2;
        this.idPareja3 = idPareja3;
    }

    public Integer getIdPareja1() {
        return idPareja1;
    }

    public void setIdPareja1(Integer idPareja1) {
        this.idPareja1 = idPareja1;
    }

    public Integer getIdPareja2() {
        return idPareja2;
    }

    public void setIdPareja2(Integer idPareja2) {
        this.idPareja2 = idPareja2;
    }

    public Integer getIdPareja3() {
        return idPareja3;
    }

    public void setIdPareja3(Integer idPareja3) {
        this.idPareja3 = idPareja3;
    }
   

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }
}
