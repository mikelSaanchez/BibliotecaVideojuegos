package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
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

    public int registrarPrestamo(int idSocio, int idCopia) {
        LocalDate hoy = LocalDate.now();
        SocioDTO socio = prestamoDAO.obtenerSocioPorId(idSocio);

        if (socio == null || socio.getAlta() == null || socio.getAlta() != 1) {
            throw new ReglaNegocioExcepcion("RN-1: El socio debe estar activo.");
        }

        return 0;
    }
}
