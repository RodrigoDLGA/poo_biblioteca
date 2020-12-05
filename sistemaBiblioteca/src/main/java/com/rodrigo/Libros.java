package com.rodrigo;

public class Libros {
    String titulo,autor,editorial,tema;
    int anio;
    int id;

    public void setId(int id){
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getTema() {
        return tema;
    }

    public int getAnio() {
        return anio;
    }

    public Libros(String titulo, String autor, String editorial, String tema, int anio, int id) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.tema = tema;
        this.anio = anio;
        this.id = id;
    }
}
