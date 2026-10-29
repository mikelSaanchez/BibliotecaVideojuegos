package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.ISocioDAO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.repository.SocioRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SocioDAO implements ISocioDAO {
    private final SocioRepository socioRepository;

    public SocioDAO(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    public ArrayList<SocioDTO> filtarSocio(String nombre, String apellido, String coreo, Integer activo) {
        return socioRepository.filtarSocio(nombre, apellido, coreo, activo);
    }
}
