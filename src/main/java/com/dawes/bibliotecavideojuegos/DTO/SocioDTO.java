package com.dawes.bibliotecavideojuegos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Datos de un socio")
public class SocioDTO {
    @Schema(description = "Identificador del socio", example = "2")
    private int id;
    @Schema(description = "Nombre", example = "Marcos")
    private String nombre;
    @Schema(description = "Apellidos", example = "Gil Prieto")
    private String apellidos;
    @Schema(description = "Correo electrónico", example = "marcos.gil@mail.test")
    private String email;
    private LocalDate fecha_alta;
    private LocalDate fecha_nacimiento;
    private Integer alta;
    private Integer edad;
    private Integer prestamosAbiertos;

    public SocioDTO() {

    }

    public SocioDTO(int id, String nombre, String apellidos, String email, LocalDate fecha_alta, LocalDate fecha_nacimiento, Integer alta, Integer edad, Integer prestamosAbiertos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.fecha_alta = fecha_alta;
        this.fecha_nacimiento = fecha_nacimiento;
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

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
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


