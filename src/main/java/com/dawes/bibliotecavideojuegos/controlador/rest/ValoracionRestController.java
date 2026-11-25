package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.ValoracionDTO;
import com.dawes.bibliotecavideojuegos.service.ValoracionesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ValoracionRestController {
    private final ValoracionesService valoracionesService;

    public ValoracionRestController(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;

    }

    @GetMapping("/valoraciones")
    public ResponseEntity<ArrayList<ValoracionDTO>> listarValoraciones(
            @RequestParam(required = false) Integer idVideojuego,
            @RequestParam(required = false) Integer idSocio,
            @RequestParam(required = false) Integer puntuacionMinima
    ) {
        ArrayList<ValoracionDTO> valoraciones = valoracionesService.filtrarValoraciones(idVideojuego, idSocio, puntuacionMinima);

        return new ResponseEntity<>(valoraciones, HttpStatus.OK);
    }

    @DeleteMapping("/valoraciones/{id}")
    public ResponseEntity borrarValoracion(@PathVariable Integer id) {
        valoracionesService.borrar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/valoraciones")
    public ResponseEntity<ValoracionDTO> insertarValoracion(@RequestBody ValoracionDTO valoracion) {
        ValoracionDTO valoracionInsertada = valoracionesService.insertarValoracion(valoracion);
        return new ResponseEntity<>(valoracionInsertada, HttpStatus.CREATED);
    }

}
