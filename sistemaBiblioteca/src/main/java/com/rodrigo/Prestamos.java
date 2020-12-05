package com.rodrigo;

public class Prestamos {
    String nombre_alumno;
    int idLibro;
    int matricula;

    public void setNombre_alumno(String nombre_alumno) {
        this.nombre_alumno = nombre_alumno;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre_alumno() {
        return nombre_alumno;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getMatricula() {
        return matricula;
    }

    public Prestamos(String nombre_alumno, int idLibro, int matricula) {
        this.nombre_alumno = nombre_alumno;
        this.idLibro = idLibro;
        this.matricula = matricula;
    }
}
