package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (edadSocio < copia.getPegi()) {
            throw new ReglaNegocioExcepcion("RN-4: La edad del socio debe ser mayor o igual que el PEGI del videojuego ");
        }

        int anioActual = LocalDate.now().getYear();

        boolean videojuegoNovedad = false;

        if (copia.getAnioLanzamiento() == anioActual || copia.getAnioLanzamiento() == anioActual - 1) {
            videojuegoNovedad = true;
        }

        LocalDate fechaLimite;
        if (videojuegoNovedad == true) {
            fechaLimite = LocalDate.now().plusDays(7);
        } else {
            fechaLimite = LocalDate.now().plusDays(15);
        }
        if (prestamoDAO.insertarPrestamo(idSocio, idCopia, hoy, fechaLimite) != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido registrar el préstamo por un error inesperado. Inténtalo de nuevo.");
        }
        return fechaLimite;
    }
}
