package com.dawes.bibliotecavideojuegos.controlador;

import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {
    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping("/listadoVideojuegos")
    public String listadoVideojuegos(Model model) {
        model.addAttribute("listaVideoJuegos",
                videojuegoService.filtrarVideojuego(null,null,null,null,null));
        return "videojuegos/listado";
    }
    @PostMapping("/listadoVideojuegos")
    public String buscarVideojuegos(@RequestParam(required = false) String titulo,
                                @RequestParam(required = false) Integer id_desarrolladora,
                                @RequestParam(required = false) Integer anio_lanzamiento,
                                @RequestParam(required = false) Integer pegi,
                                @RequestParam(required = false) Boolean activo,
                                Model model) {
        model.addAttribute("listaVideoJuegos",
                videojuegoService.filtrarVideojuego(titulo, id_desarrolladora, anio_lanzamiento, pegi, activo));
        return "videojuegos/listado";
    }

}

