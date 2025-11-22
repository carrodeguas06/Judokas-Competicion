package com.liceolapaz.bcd.judokascompeticion.pojo;

public class Judoka {
    private int id;
    private String nombre;
    private String apellido;
    private String pais;
    private String cinturon;
    private int tecnica_especial;

    public Judoka() {
    }

    public Judoka(int id,String nombre, String apellido, String pais, String cinturon, int tecnica_especial) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
        this.cinturon = cinturon;
        this.tecnica_especial = tecnica_especial;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCinturon() {
        return cinturon;
    }

    public void setCinturon(String cinturon) {
        this.cinturon = cinturon;
    }

    public int getTecnica_especial() {
        return tecnica_especial;
    }

    public void setTecnica_especial(int tecnica_especial) {
        this.tecnica_especial = tecnica_especial;
    }
}
