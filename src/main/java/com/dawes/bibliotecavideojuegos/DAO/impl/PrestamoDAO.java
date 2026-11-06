package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.repository.PrestamoRepository;
import com.dawes.bibliotecavideojuegos.repository.SocioRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoDAO implements IPrestamoDAO {
    private final PrestamoRepository prestamoRepository;
    private final SocioRepository socioRepository;

    public PrestamoDAO(PrestamoRepository prestamoRepository,SocioRepository socioRepository ) {
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
}
