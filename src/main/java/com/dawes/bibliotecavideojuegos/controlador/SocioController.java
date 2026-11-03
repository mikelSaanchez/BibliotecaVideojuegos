package com.dawes.bibliotecavideojuegos.controlador;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import com.dawes.bibliotecavideojuegos.service.SocioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping("/listadoSocios")
    public String listadoSocios(Model model) {
        model.addAttribute("listadoSocios", socioService.filtarSocio(null, null, null, null));

        return "socios/listado";
    }

    @PostMapping("/listadoSocios")
    public String buscarSocio(@RequestParam(required = false) String nombre, @RequestParam(required = false) String apellido, @RequestParam(required = false) String correo, @RequestParam(required = false) Integer activo, Model model) {

        model.addAttribute("listadoSocios", socioService.filtarSocio(nombre, apellido, correo, activo));
        return "socios/listado";
    }

    @GetMapping("/insertarSocio")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("socio", new SocioDTO());

        return "socios/insertar";
    }

    @PostMapping("/insertarSocio")
    public String insertarSocio(@ModelAttribute("socio") SocioDTO socio, Model model) {
        try {

            socioService.insertarSocio(socio);
            model.addAttribute("mensaje", "Socio " + socio.getNombre() + " insertado correctamente.");
            model.addAttribute("socio", new SocioDTO());

        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        return "socios/insertar";
    }
}
