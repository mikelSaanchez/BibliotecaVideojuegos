package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.ISocioDAO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SocioService {
    private final ISocioDAO socioDAO;


    public SocioService(ISocioDAO socioDAO) {
        this.socioDAO = socioDAO;
    }

    public ArrayList<SocioDTO> filtarSocio(String nombre, String apellido, String coreo, Integer activo) {
        return socioDAO.filtarSocio(nombre, apellido, coreo, activo);
    }
}
