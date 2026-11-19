package com.dawes.bibliotecavideojuegos.controlador.rest;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class SocioRestController {
    private final SocioService socioService;

    public SocioRestController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping("/socios")
    public ResponseEntity<ArrayList<SocioDTO>> listarSocios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer activo
    ) {
        ArrayList<SocioDTO> socios = socioService.filtarSocio(nombre, apellido, email, activo);
        return new ResponseEntity<>(socios, HttpStatus.OK);
    }

    @GetMapping("/socios/{id}")
    public ResponseEntity<SocioDTO> buscarSociosPorId(@PathVariable Integer id) {
        return new ResponseEntity<>(socioService.obtener(id), HttpStatus.OK);
    }
}
