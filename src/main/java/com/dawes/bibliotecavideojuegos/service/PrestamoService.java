package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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

        if (copia == null) {
            throw new ReglaNegocioExcepcion("RN-2: La copia seleccionada no existe o no está disponible.");
        }

        if (prestamoDAO.contarPrestamosAbiertos(idSocio) >= 3) {
            throw new ReglaNegocioExcepcion("RN-3: Un socio no puede tener más de 3 préstamos abiertos a la vez.");
        }

        int edadSocio = ((socio.getFecha_nacimiento()).getYear() - LocalDate.now().getYear());

        if (edadSocio > copia.getPegi()) {
            throw new ReglaNegocioExcepcion("RN-4: La edad del socio debe ser mayor o igual que el PEGI del videojuego ");
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

        if (prestamo.getFechaLimite().isBefore(LocalDate.now())) {
            throw new ReglaNegocioExcepcion("RN-RE1. No se puede renovar un préstamo vencido.");
        }

        if (prestamo.getRenovaciones() >= 1) {
            throw new ReglaNegocioExcepcion("RN-RE2. Como máximo se puede renovar una vez cada préstamo.");
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
}