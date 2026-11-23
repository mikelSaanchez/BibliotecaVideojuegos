package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class PrestamoRestController {
    private final PrestamoService prestamoService;

    public PrestamoRestController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(summary = "Lista préstamos", description = "Busca préstamos con filtros opcionales y combinables.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de préstamos, posiblemente vacío"),
            @ApiResponse(responseCode = "400", description = "Estado o fecha no válidos")
    })
    @GetMapping("/prestamos")
    public ResponseEntity<ArrayList<PrestamoDTO>> listarPrestamos(
            @RequestParam(required = false) String socio,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate fechaDesde) {
        return ResponseEntity.ok(prestamoService.listarParaApi(socio, titulo, estado, fechaDesde));
    }
    @Operation(summary = "Consulta un préstamo", description = "Devuelve el préstamo y su estado calculado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Préstamo inexistente")
    })
    @GetMapping("/prestamos/{id}")
    public ResponseEntity<PrestamoDTO> obtenerPrestamoPorID(@PathVariable int id) {
        return ResponseEntity.ok(prestamoService.obtenerParaApi(id));
    }



}
