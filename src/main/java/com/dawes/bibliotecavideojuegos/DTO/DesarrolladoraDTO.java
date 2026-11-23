package com.dawes.bibliotecavideojuegos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Desarrolladora de videojuegos")
public class DesarrolladoraDTO {
    @Schema(example = "1")
    private int id;
    @Schema(example = "Nintendo")
    private String nombre;
    @Schema(example = "Japón")
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