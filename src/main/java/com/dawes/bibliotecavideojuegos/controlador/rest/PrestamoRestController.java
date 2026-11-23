package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class PrestamoRestController {
    private final PrestamoService prestamoService;

    public PrestamoRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/prestamos")
    public ResponseEntity<ArrayList<PrestamoDTO>> listarPrestamos(
            @RequestParam(required = false) String socio,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fechaDesde) {
        return ResponseEntity.ok(prestamoService.listarParaApi(socio, titulo, estado, fechaDesde));
    }
}
