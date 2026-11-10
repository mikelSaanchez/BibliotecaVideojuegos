package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.repository.PrestamoRepository;
import com.dawes.bibliotecavideojuegos.repository.SocioRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoDAO implements IPrestamoDAO {
    private final PrestamoRepository prestamoRepository;
    private final SocioRepository socioRepository;

    public PrestamoDAO(PrestamoRepository prestamoRepository, SocioRepository socioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.socioRepository = socioRepository;
    }

    @Override
    public ArrayList<DesplegableDTO> listarSociosActivos() {
        return prestamoRepository.listarSociosActivos();
    }

    @Override
    public ArrayList<DesplegableDTO> listarCopiasDisponibles() {
        return prestamoRepository.listarCopiasDisponibles();
    }

    @Override
    public int insertarPrestamo(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        return prestamoRepository.insertarPrestamo(idSocio, idCopia, fechaPrestamo, fechaLimite);
    }

    @Override
    public SocioDTO obtenerSocioPorId(int idSocio) {
        return socioRepository.obtenerSocioPorId(idSocio);
    }

    @Override
    public CopiaDTO obtenerCopia(int idCopia) {
        return prestamoRepository.obtenerCopia(idCopia);
    }

    @Override
    public boolean copiaDisponible(int idCopia) {
        return prestamoRepository.copiaDisponible(idCopia);
    }

    @Override
    public int contarPrestamosAbiertos(int idSocio) {
        return prestamoRepository.contarPrestamosAbiertos(idSocio);
    }

    @Override
    public boolean tienePrestamoVencido(int idSocio, LocalDate hoy) {
        return prestamoRepository.tienePrestamoVencido(idSocio, hoy);
    }

    @Override
    public ArrayList<PrestamoDTO> listarPrestamos(String socio, String titulo, LocalDate fechaDesde, boolean soloAbiertos) {
        return prestamoRepository.listarPrestamos(socio, titulo, fechaDesde, soloAbiertos);
    }

    @Override
    public boolean devolverPrestamo(int idPrestamo, int idCopia, LocalDate fechaDevolucion, BigDecimal multa, boolean copiaDanada) {
        return prestamoRepository.devolverPrestamo(idPrestamo, idCopia, fechaDevolucion, multa, copiaDanada);
    }
}
