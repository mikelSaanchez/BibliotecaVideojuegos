package com.dawes.bibliotecavideojuegos.controlador.thymeleaf;

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
        prepararFiltros(model, null, null, null, null, false);
        return "videojuegos/listado";
    }

    @PostMapping("/listadoVideojuegos")
    public String buscarVideojuegos(@RequestParam(required = false) String titulo,
                                    @RequestParam(required = false) Integer id_desarrolladora,
                                    @RequestParam(required = false) Integer anio_lanzamiento,
                                    @RequestParam(required = false) Integer pegi,
                                    @RequestParam(defaultValue = "false") Boolean soloDisponibles,
                                    Model model) {
        model.addAttribute("listaVideoJuegos", videojuegoService.filtrarVideojuego(titulo, id_desarrolladora, anio_lanzamiento, pegi, soloDisponibles));
        model.addAttribute("desplegableDesarrolladora", videojuegoService.obtenerDesplegableDesarrolladoras());
        prepararFiltros(model, titulo, id_desarrolladora, anio_lanzamiento, pegi, soloDisponibles);
        return "videojuegos/listado";
    }

    private void prepararFiltros(Model model, String titulo, Integer desarrolladora, Integer anio, Integer pegi, Boolean soloDisponibles) {
        model.addAttribute("tituloFiltro", titulo);
        model.addAttribute("desarrolladoraFiltro", desarrolladora);
        model.addAttribute("anioFiltro", anio);
        model.addAttribute("pegiFiltro", pegi);
        model.addAttribute("soloDisponiblesFiltro", Boolean.TRUE.equals(soloDisponibles));
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
            model.addAttribute("mensaje", "Videojuego " + videojuego.getTitulo() + " insertado correctamente.");
            model.addAttribute("videojuego", new VideojuegoDTO());

        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("desplegableDesarrolladora", videojuegoService.obtenerDesplegableDesarrolladoras());
        return "videojuegos/insertar";
    }

    @GetMapping("/VideoJuegosMasPrestados")
    public String ListadoVideoJuegosMasPrestados(Model model) {
        model.addAttribute("listaVideoJuegos", videojuegoService.VideoJuegosMasPrestados());

        return "videojuegos/masPrestados";
    }

}



