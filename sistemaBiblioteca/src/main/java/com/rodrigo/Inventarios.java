package com.rodrigo;

public class Inventarios {
    String titulo, estatus;
    int id;
    int cantidad;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstatus() {
        return estatus;
    }

    public int getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Inventarios(String titulo, String estatus, int id, int cantidad) {
        this.id = id;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.estatus = estatus;
    }
}
