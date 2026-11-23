package com.dawes.bibliotecavideojuegos.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Indica si la copia vuelve dañada")
public class DevolucionSolicitudDTO {
    @JsonProperty("copiaDañada")
    @Schema(description = "Marca la copia como dañada al devolverla", example = "false")
    private boolean copiaDanada;

    public boolean isCopiaDanada() { return copiaDanada; }
    public void setCopiaDanada(boolean copiaDanada) { this.copiaDanada = copiaDanada; }
}
