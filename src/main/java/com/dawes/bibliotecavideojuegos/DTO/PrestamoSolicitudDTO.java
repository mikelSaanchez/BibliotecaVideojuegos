package com.dawes.bibliotecavideojuegos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para registrar un préstamo")
public class PrestamoSolicitudDTO {
    @Schema(example = "2")
    private Integer idSocio;
    @Schema(example = "7")
    private Integer idCopia;

    public Integer getIdSocio() { return idSocio; }
    public void setIdSocio(Integer idSocio) { this.idSocio = idSocio; }
    public Integer getIdCopia() { return idCopia; }
    public void setIdCopia(Integer idCopia) { this.idCopia = idCopia; }
}
