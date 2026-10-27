package com.dawes.bibliotecavideojuegos.DTO;

public class DesplegableDTO {
    private int id;
    private String nombre;

    public DesplegableDTO() {
    }

    public DesplegableDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public void setNombre(String descripcion) {
        this.nombre = nombre;
    }
}
