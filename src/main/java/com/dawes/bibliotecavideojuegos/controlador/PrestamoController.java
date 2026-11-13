package com.dawes.bibliotecavideojuegos.controlador;


import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import com.dawes.bibliotecavideojuegos.service.PrestamoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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

        if (idSocio == null || idCopia == null) {
            model.addAttribute("error", "Debes seleccionar un socio y una copia.");
            return "prestamos/registrar";
        }

        try {
            LocalDate fechaLimite = prestamoService.registrarPrestamo(idSocio, idCopia);
            model.addAttribute("mensaje", "Préstamo registrado correctamente. Fecha límite: " + fechaLimite + ".");
            model.addAttribute("copiasDisponibles", prestamoService.obtenerCopiasDisponibles());
        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }

        return "prestamos/registrar";
    }

    @GetMapping("/listadoPrestamos")
    public String listadoPrestamos(@RequestParam(defaultValue = "TODOS") String estado, Model model) {
        cargarListado(model, null, null, estado, null);
        return "prestamos/listado";
    }

    @PostMapping("/listadoPrestamos")
    public String buscarPrestamos(@RequestParam int idPrestamo,
                                  @RequestParam(required = false) String socio,
                                  @RequestParam(required = false) String titulo,
                                  @RequestParam(defaultValue = "TODOS") String estado,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                                  Model model) {
        cargarListado(model, socio, titulo, estado, fechaDesde);
        try {
            LocalDate nuevaFechaLimite = prestamoService.renovarPrestamo(idPrestamo);
            model.addAttribute("mensaje", "Préstamo renovado correctamente. Nueva fecha límite: " + nuevaFechaLimite + ".");
        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("listaPrestamos", prestamoService.listarPrestamosAbiertos(socio));
        model.addAttribute("socioFiltro", socio);
        return "prestamos/listado";
    }

    @GetMapping("/devolverPrestamo")
    public String mostrarDevoluciones(@RequestParam(required = false) String socio, Model model) {
        model.addAttribute("listaPrestamos", prestamoService.listarPrestamosAbiertos(socio));
        model.addAttribute("socioFiltro", socio);
        return "prestamos/devolver";
    }

    @PostMapping("/devolverPrestamo")
    public String devolverPrestamo(@RequestParam int idPrestamo,
                                   @RequestParam(defaultValue = "false") boolean copiaDanada,
                                   @RequestParam(required = false) String socio,
                                   Model model) {
        try {
            BigDecimal multa = prestamoService.devolverPrestamo(idPrestamo, copiaDanada);
            model.addAttribute("mensaje", "Préstamo devuelto correctamente. Multa calculada: " + multa + " €.");
        } catch (ReglaNegocioExcepcion e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("listaPrestamos", prestamoService.listarPrestamosAbiertos(socio));
        model.addAttribute("socioFiltro", socio);
        return "prestamos/devolver";
    }

    private void cargarListado(Model model, String socio, String titulo, String estado, LocalDate fechaDesde) {
        model.addAttribute("listaPrestamos", prestamoService.listarPrestamos(socio, titulo, estado, fechaDesde));
        model.addAttribute("socioFiltro", socio);
        model.addAttribute("tituloFiltro", titulo);
        model.addAttribute("estadoFiltro", estado);
        model.addAttribute("fechaDesdeFiltro", fechaDesde);
    }

}