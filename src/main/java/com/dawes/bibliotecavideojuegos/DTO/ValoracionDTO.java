package com.dawes.bibliotecavideojuegos.DTO;

import java.time.LocalDate;

public class ValoracionDTO {
    private Integer id;
    private Integer idSocio;
    private String nombreSocio;
    private Integer idVideojuego;
    private String tituloVideojuego;
    private Double puntuacion;
    private String comentario;
    private LocalDate fecha;

    public ValoracionDTO() {}

    public ValoracionDTO(Integer id, Integer idSocio, String nombreSocio, Integer idVideojuego, String tituloVideojuego, Double puntuacion, String comentario, LocalDate fecha) {
        this.id = id;
        this.idSocio = idSocio;
        this.nombreSocio = nombreSocio;
        this.idVideojuego = idVideojuego;
        this.tituloVideojuego = tituloVideojuego;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public Integer getIdVideojuego() {
        return idVideojuego;
    }

    public void setIdVideojuego(Integer idVideojuego) {
        this.idVideojuego = idVideojuego;
    }

    public String getTituloVideojuego() {
        return tituloVideojuego;
    }

    public void setTituloVideojuego(String tituloVideojuego) {
        this.tituloVideojuego = tituloVideojuego;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
