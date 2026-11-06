package com.dawes.bibliotecavideojuegos.controlador;


import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping("/registrarPrestamo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("sociosActivos", prestamoService.obtenerSociosActivos());
        model.addAttribute("copiasDisponibles", prestamoService.obtenerCopiasDisponibles());
        return "prestamos/registrar";
    }

    @PostMapping("/registrarPrestamo")
    public String registrarPrestamo(@RequestParam(required = false) Integer idSocio,
                                    @RequestParam(required = false) Integer idCopia,
                                    Model model) {
        model.addAttribute("sociosActivos", prestamoService.obtenerSociosActivos());
        model.addAttribute("copiasDisponibles", prestamoService.obtenerCopiasDisponibles());

        LocalDate fechaLimite = prestamoService.registrarPrestamo(idSocio, idCopia);
        model.addAttribute("mensaje", "Préstamo registrado correctamente. Fecha límite: " + fechaLimite + ".");


        return "prestamos/registrar";
    }
}