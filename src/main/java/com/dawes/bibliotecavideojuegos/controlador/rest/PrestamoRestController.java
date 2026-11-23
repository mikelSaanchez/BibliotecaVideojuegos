package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.DTO.PrestamoSolicitudDTO;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @Operation(summary = "Registra un préstamo", description = "Comprueba RN-1 a RN-5 y crea el préstamo.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Préstamo creado"),
            @ApiResponse(responseCode = "400", description = "Body no válido"),
            @ApiResponse(responseCode = "404", description = "Socio o copia inexistentes"),
            @ApiResponse(responseCode = "422", description = "No se cumple una regla RN-1 a RN-5")
    })
    @PostMapping("/prestamos")
    public ResponseEntity<PrestamoDTO> registrarPrestamo(@RequestBody PrestamoSolicitudDTO solicitud) {
        if (solicitud == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud es obligatorio.");
        }
        Integer idSocio = solicitud.getIdSocio();
        Integer idCopia = solicitud.getIdCopia();
        PrestamoDTO prestamo = prestamoService.registrarParaApi(idSocio, idCopia);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
    }




}
