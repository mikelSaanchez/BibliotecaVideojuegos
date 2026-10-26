package com.dawes.bibliotecavideojuegos.DTO;

public class VideojuegoDTO {
    private int id;
    private String titulo;
    private Integer anio_lanzamiento;
    private Integer pegi;
    private Integer id_desarrolladora;
    private String desarrolladora;
    private boolean disponible;
    private Integer copiasTotales;
    private Integer copiasDisponibles;

    public VideojuegoDTO() {
    }

    public VideojuegoDTO(int id, String titulo, Integer anio_lanzamiento, Integer pegi, Integer id_desarrolladora, String desarrolladora, boolean disponible, Integer copiasTotales, Integer copiasDisponibles) {
        this.id = id;
        this.titulo = titulo;
        this.anio_lanzamiento = anio_lanzamiento;
        this.pegi = pegi;
        this.id_desarrolladora = id_desarrolladora;
        this.desarrolladora = desarrolladora;
        this.disponible = disponible;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasDisponibles;
    }

    // Para listar
    public VideojuegoDTO(int id, String titulo, String desarrolladora, Integer anio_lanzamiento, Integer pegi, Integer copiasTotales, Integer copiasDisponibles) {
        this.id = id;
        this.titulo = titulo;
        this.desarrolladora = desarrolladora;
        this.anio_lanzamiento = anio_lanzamiento;
        this.pegi = pegi;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasDisponibles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio_lanzamiento() {
        return anio_lanzamiento;
    }

    public void setAnio_lanzamiento(Integer anio_lanzamiento) {
        this.anio_lanzamiento = anio_lanzamiento;
    }

    public Integer getPegi() {
        return pegi;
    }

    public void setPegi(Integer pegi) {
        this.pegi = pegi;
    }

    public Integer getId_desarrolladora() {
        return id_desarrolladora;
    }

    public void setId_desarrolladora(Integer id_desarrolladora) {
        this.id_desarrolladora = id_desarrolladora;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getCopiasTotales() {
        return copiasTotales;
    }

    public void setCopiasTotales(Integer copiasTotales) {
        this.copiasTotales = copiasTotales;
    }

    public Integer getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(Integer copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }
}
