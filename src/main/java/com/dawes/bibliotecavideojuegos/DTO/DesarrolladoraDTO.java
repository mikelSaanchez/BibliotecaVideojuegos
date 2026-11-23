package com.dawes.bibliotecavideojuegos.DTO;

public class DesarrolladoraDTO {
    private Integer id;
    private String nombre;
    private String pais;

    public DesarrolladoraDTO() {
    }

    public DesarrolladoraDTO(Integer id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}