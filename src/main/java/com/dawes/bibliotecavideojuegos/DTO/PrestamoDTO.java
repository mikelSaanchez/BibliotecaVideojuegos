package com.dawes.bibliotecavideojuegos.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PrestamoDTO {
    private Integer id;
    private Integer idSocio;
    private Integer idCopia;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private BigDecimal multa;
    private String socio;
    private String titulo;
    private String plataforma;
    private String codigoInventario;
    private String estadoCalculado;
    private long diasRetraso;
    private boolean copiaDanada;

    public PrestamoDTO() {
    }

    public PrestamoDTO(Integer id, Integer idSocio, Integer idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion, BigDecimal multa, String socio, String titulo, String plataforma, String codigoInventario, String estadoCalculado, long diasRetraso, boolean copiaDanada) {
        this.id = id;
        this.idSocio = idSocio;
        this.idCopia = idCopia;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.multa = multa;
        this.socio = socio;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.codigoInventario = codigoInventario;
        this.estadoCalculado = estadoCalculado;
        this.diasRetraso = diasRetraso;
        this.copiaDanada = copiaDanada;
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

    public Integer getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(Integer idCopia) {
        this.idCopia = idCopia;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getCodigoInventario() {
        return codigoInventario;
    }

    public void setCodigoInventario(String codigoInventario) {
        this.codigoInventario = codigoInventario;
    }

    public String getEstadoCalculado() {
        return estadoCalculado;
    }

    public void setEstadoCalculado(String estadoCalculado) {
        this.estadoCalculado = estadoCalculado;
    }

    public long getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(long diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public boolean isCopiaDanada() {
        return copiaDanada;
    }

    public void setCopiaDanada(boolean copiaDanada) {
        this.copiaDanada = copiaDanada;
    }
}



