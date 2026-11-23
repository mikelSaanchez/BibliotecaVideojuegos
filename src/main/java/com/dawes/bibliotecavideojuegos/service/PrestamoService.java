package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class PrestamoService {
    private final IPrestamoDAO prestamoDAO;


    public PrestamoService(IPrestamoDAO prestamoDAO) {
        this.prestamoDAO = prestamoDAO;
    }

    public ArrayList<DesplegableDTO> obtenerSociosActivos() {
        return prestamoDAO.listarSociosActivos();
    }

    public ArrayList<DesplegableDTO> obtenerCopiasDisponibles() {
        return prestamoDAO.listarCopiasDisponibles();
    }

    public LocalDate registrarPrestamo(int idSocio, int idCopia) {

        LocalDate hoy = LocalDate.now();
        SocioDTO socio = prestamoDAO.obtenerSocioPorId(idSocio);

        if (socio == null || socio.getAlta() == null || socio.getAlta() != 1) {
            throw new ReglaNegocioExcepcion("RN-1: El socio debe estar activo.");
        }

        CopiaDTO copia = prestamoDAO.obtenerCopia(idCopia);

        if (copia == null || !prestamoDAO.copiaDisponible(idCopia)) {
            throw new ReglaNegocioExcepcion("RN-2: La copia seleccionada no existe o no está disponible.");
        }

        if (prestamoDAO.contarPrestamosAbiertos(idSocio) >= 3) {
            throw new ReglaNegocioExcepcion("RN-3: Un socio no puede tener más de 3 préstamos abiertos a la vez.");
        }

        int edadSocio = Period.between(socio.getFecha_nacimiento(), hoy).getYears();

        if (edadSocio < copia.getPegi()) {
            throw new ReglaNegocioExcepcion("RN-4: El socio no tiene la edad mínima del PEGI del videojuego.");
        }

        if (prestamoDAO.tienePrestamoVencido(idSocio, hoy)) {
            throw new ReglaNegocioExcepcion("RN-5: El socio tiene un préstamo vencido sin devolver.");
        }

        int anioActual = LocalDate.now().getYear();
        boolean videojuegoNovedad = false;

        if (copia.getAnioLanzamiento() == anioActual || copia.getAnioLanzamiento() == anioActual - 1) {
            videojuegoNovedad = true;
        }

        LocalDate fechaLimite;

        if (videojuegoNovedad) {
            fechaLimite = LocalDate.now().plusDays(7);
        } else {
            fechaLimite = LocalDate.now().plusDays(15);
        }

        int filasAfectadas = prestamoDAO.insertarPrestamo(idSocio, idCopia, hoy, fechaLimite);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido registrar el préstamo por un error inesperado. Inténtalo de nuevo.");
        }

        return fechaLimite;
    }

    public ArrayList<PrestamoDTO> listarPrestamos(String socio, String titulo, String estado, LocalDate fechaDesde) {
        ArrayList<PrestamoDTO> prestamos = prestamoDAO.listarPrestamos(socio, titulo, fechaDesde, false);

        for (PrestamoDTO prestamo : prestamos) {
            calcularEstado(prestamo, LocalDate.now());
        }

        if (estado == null || estado.isBlank() || "TODOS".equals(estado)) {
            return prestamos;
        }

        ArrayList<PrestamoDTO> resultado = new ArrayList<>();

        for (PrestamoDTO prestamo : prestamos) {
            if (estado.equals(prestamo.getEstadoCalculado())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }

    public BigDecimal devolverPrestamo(int idPrestamo, boolean copiaDanada) {

        ArrayList<PrestamoDTO> prestamosAbiertos = prestamoDAO.listarPrestamos(null, null, null, true);
        PrestamoDTO prestamo = null;

        for (PrestamoDTO prestamoAbierto : prestamosAbiertos) {
            if (prestamoAbierto.getId().equals(idPrestamo)) {
                prestamo = prestamoAbierto;
                break;
            }
        }

        if (prestamo == null) {
            throw new ReglaNegocioExcepcion("El préstamo ya está devuelto o no existe.");
        }

        LocalDate hoy = LocalDate.now();
        long diasRetraso = Math.max(0, ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy));
        BigDecimal multa = BigDecimal.valueOf(diasRetraso).multiply(new BigDecimal("0.50")).setScale(2, RoundingMode.HALF_UP);

        boolean devuelto = prestamoDAO.devolverPrestamo(idPrestamo, prestamo.getIdCopia(), hoy, multa, copiaDanada);

        if (!devuelto) {
            throw new ReglaNegocioExcepcion("No se ha podido devolver el préstamo. Puede que ya se haya devuelto.");
        }

        return multa;
    }

    public ArrayList<PrestamoDTO> listarPrestamosAbiertos(String socio) {
        ArrayList<PrestamoDTO> prestamos = prestamoDAO.listarPrestamos(socio, null, null, true);

        for (PrestamoDTO prestamo : prestamos) {
            calcularEstado(prestamo, LocalDate.now());
        }

        return prestamos;
    }

    public LocalDate renovarPrestamo(int idPrestamo) {

        ArrayList<PrestamoDTO> prestamosAbiertos = prestamoDAO.listarPrestamos(null, null, null, true);
        PrestamoDTO prestamo = null;

        for (PrestamoDTO prestamoAbierto : prestamosAbiertos) {
            if (prestamoAbierto.getId().equals(idPrestamo)) {
                prestamo = prestamoAbierto;
                break;
            }
        }

        if (prestamo == null) {
            throw new ReglaNegocioExcepcion("No se puede renovar el préstamo porque no existe o ya está devuelto.");
        }

        if (prestamo.getFechaLimite().isBefore(LocalDate.now())) {
            throw new ReglaNegocioExcepcion("RN-RE1: No se puede renovar el préstamo " + idPrestamo + ": venció el " + prestamo.getFechaLimite() + ".");
        }

        if (prestamo.getRenovaciones() >= 1) {
            throw new ReglaNegocioExcepcion("RN-RE2: El préstamo " + idPrestamo + " ya se ha renovado una vez.");
        }

        LocalDate nuevaFechaLimite = prestamo.getFechaLimite().plusDays(7);
        int filasAfectadas = prestamoDAO.renovarPrestamo(idPrestamo, nuevaFechaLimite);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido renovar el préstamo. Puede que ya se haya devuelto.");
        }
        return nuevaFechaLimite;
    }

    private void calcularEstado(PrestamoDTO prestamo, LocalDate hoy) {
        if (prestamo.getFechaDevolucion() != null) {
            prestamo.setEstadoCalculado("DEVUELTO");
            prestamo.setDiasRetraso(0);
        } else if (prestamo.getFechaLimite().isBefore(hoy)) {
            prestamo.setEstadoCalculado("VENCIDO");
            prestamo.setDiasRetraso(ChronoUnit.DAYS.between(prestamo.getFechaLimite(), hoy));
        } else {
            prestamo.setEstadoCalculado("EN_PLAZO");
            prestamo.setDiasRetraso(0);
        }
    }

    public ArrayList<PrestamoDTO> listarParaApi(String socio, String titulo, String estado, LocalDate fechaDesde) {
        if (estado != null && !estado.isBlank()
                && !estado.equals("EN_PLAZO") && !estado.equals("VENCIDO") && !estado.equals("DEVUELTO")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El estado debe ser EN_PLAZO, VENCIDO o DEVUELTO.");
        }
        ArrayList<PrestamoDTO> prestamos = prestamoDAO.listarPrestamos(socio, titulo, fechaDesde, false);
        for (PrestamoDTO prestamo : prestamos) {
            calcularEstado(prestamo, LocalDate.now());
        }
        if (estado == null || estado.isBlank()) {
            return prestamos;
        }
        ArrayList<PrestamoDTO> resultado = new ArrayList<>();
        for (PrestamoDTO prestamo : prestamos) {
            if (estado.equals(prestamo.getEstadoCalculado())) {
                resultado.add(prestamo);
            }
        }
        return resultado;
    }

}
