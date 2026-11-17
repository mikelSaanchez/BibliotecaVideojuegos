package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class VideojuegoRestController {
    private final VideojuegoService videojuegoService;

    public VideojuegoRestController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping("/videojuegos")
    public ResponseEntity<ArrayList<VideojuegoDTO>> listarVideojuegos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer idDesarrolladora,
            @RequestParam(required = false) Integer anio_lanzamiento,
            @RequestParam(required = false) Integer pegi,
            @RequestParam(required = false) Boolean activo
    ) {
        ArrayList<VideojuegoDTO> videojuegos = videojuegoService.filtrarVideojuego(titulo, idDesarrolladora, anio_lanzamiento, pegi, activo);
        return new ResponseEntity<>(videojuegos, HttpStatus.OK);
    }

    @GetMapping("/videojuegos/{id}")
    public ResponseEntity<VideojuegoDTO> buscarVideojuegoPorId(@PathVariable Integer id){
        return new ResponseEntity<>(videojuegoService.buscarVideojuegoPorID(id), HttpStatus.OK);
    }

    @PostMapping("/videojuegos")
    public ResponseEntity<VideojuegoDTO> insertarVideojuego(@RequestBody VideojuegoDTO videojuego){
        VideojuegoDTO videojuegoInsertado = videojuegoService.insertarVideojuegoRest(videojuego);
        return new ResponseEntity<>(videojuegoInsertado, HttpStatus.CREATED);
    }


}
