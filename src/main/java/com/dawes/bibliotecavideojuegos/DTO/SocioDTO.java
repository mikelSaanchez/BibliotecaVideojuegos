package com.dawes.bibliotecavideojuegos.DTO;

import java.time.LocalDate;

public class SocioDTO {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private LocalDate fecha_alta;
    private Integer alta;
    private Integer edad;
    private Integer prestamosAbiertos;

    public SocioDTO() {

    }

    public SocioDTO(int id, String nombre, String apellidos, String email, LocalDate fecha_alta, Integer alta, Integer edad, Integer prestamosAbiertos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fecha_alta = fecha_alta;
        this.alta = alta;
        this.edad = edad;
        this.prestamosAbiertos = prestamosAbiertos;
    }

    //Para listar
    public SocioDTO(int id, String nombre, String apellidos, String email, Integer edad, Integer alta, Integer prestamosAbiertos, LocalDate fecha_alta) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.edad = edad;
        this.alta = alta;
        this.prestamosAbiertos = prestamosAbiertos;
        this.fecha_alta = fecha_alta;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(LocalDate fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Integer getAlta() {
        return alta;
    }

    public void setAlta(Integer alta) {
        this.alta = alta;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPrestamosAbiertos() {
        return prestamosAbiertos;
    }

    public void setPrestamosAbiertos(Integer prestamosAbiertos) {
        this.prestamosAbiertos = prestamosAbiertos;
    }
}


