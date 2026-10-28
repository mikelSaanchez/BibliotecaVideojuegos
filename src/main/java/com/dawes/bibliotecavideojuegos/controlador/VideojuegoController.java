package com.dawes.bibliotecavideojuegos.controlador;

import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import com.dawes.bibliotecavideojuegos.service.VideojuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {
    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping("/listadoVideojuegos")
    public String listadoVideojuegos(Model model) {
        model.addAttribute("listaVideoJuegos", videojuegoService.filtrarVideojuego(null, null, null, null, null));
        model.addAttribute("desplegableDesarrolladora", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/listado";
    }

    @PostMapping("/listadoVideojuegos")
    public String buscarVideojuegos(@RequestParam(required = false) String titulo,
                                    @RequestParam(required = false) Integer id_desarrolladora,
                                    @RequestParam(required = false) Integer anio_lanzamiento,
                                    @RequestParam(required = false) Integer pegi,
                                    @RequestParam(required = false) Boolean activo,
                                    Model model) {
        model.addAttribute("listaVideoJuegos", videojuegoService.filtrarVideojuego(titulo, id_desarrolladora, anio_lanzamiento, pegi, activo));
        model.addAttribute("desplegableDesarrolladora", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/listado";
    }
    @GetMapping("/insertarVideojuego")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("videojuego", new VideojuegoDTO());
        model.addAttribute("desplegableDesarrolladora", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/insertar";
    }

    @PostMapping("/insertarVideojuego")
    public String insertarVideoJuego(@ModelAttribute("videojuego") VideojuegoDTO videojuego,
                                 Model model) {
        try {

            videojuegoService.insertarVideojuego(videojuego);
            model.addAttribute("mensaje", "Videojuego " + videojuego.getTitulo() +  " insertado correctamente.");
            model.addAttribute("videojuego", new VideojuegoDTO());

        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("desplegableDesarrolladoras", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/insertar";
    }

}

