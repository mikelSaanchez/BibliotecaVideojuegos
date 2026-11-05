package com.dawes.bibliotecavideojuegos.controlador;

import com.dawes.bibliotecavideojuegos.DTO.InicioDTO;
import com.dawes.bibliotecavideojuegos.service.InicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioController {
    private final InicioService inicioService;

    public InicioController(InicioService inicioService) {
        this.inicioService = inicioService;
    }

    @GetMapping
    public String listarInicio(Model model){
        model.addAttribute("listarInicio",inicioService.mapearInicio());
        return "index";
    }

}
