package com.dawes.bibliotecavideojuegos.controlador;

import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping("/listadoSocios")
    public String listadoSocios(Model model) {
        model.addAttribute("listaSocios", socioService.filtarSocio(null, null, null, null));

        return "socios/listado";
    }

    @PostMapping("/listadoSocios")
    public String buscarSocio(@RequestParam(required = false) String nombre,
                              @RequestParam(required = false) String apellido,
                              @RequestParam(required = false) String correo,
                              @RequestParam(required = false) Integer activo,
                              Model model) {
        model.addAttribute("listadoSocios", socioService.filtarSocio(nombre, apellido, correo, activo));
        return "socios/listado";

    }
}
