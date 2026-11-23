package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.DTO.InicioDTO;
import com.dawes.bibliotecavideojuegos.service.InicioService;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class RecursosRestController {
    private final VideojuegoService videojuegoService;
    private final InicioService inicioService;

    public RecursosRestController(VideojuegoService videojuegoService, InicioService inicioService) {
        this.videojuegoService = videojuegoService;
        this.inicioService = inicioService;
    }

    @GetMapping("/desarrolladoras")
    public ResponseEntity<ArrayList<DesarrolladoraDTO>> listarDesarrolladoras() {
        return ResponseEntity.ok(videojuegoService.listarDesarrolladorasApi());
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<InicioDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(inicioService.mapearInicio());
    }
}