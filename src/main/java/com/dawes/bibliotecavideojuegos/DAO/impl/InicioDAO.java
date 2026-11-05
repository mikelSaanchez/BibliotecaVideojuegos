package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IInicoDAO;
import com.dawes.bibliotecavideojuegos.DTO.InicioDTO;
import com.dawes.bibliotecavideojuegos.repository.InicioRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InicioDAO implements IInicoDAO {

    private final InicioRepository inicioRepository;

    public InicioDAO(InicioRepository inicioRepository) {
        this.inicioRepository = inicioRepository;
    }

    @Override
    public InicioDTO mapearInicio() {
        return inicioRepository.mapearInicio();
    }
}
