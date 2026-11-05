package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.impl.InicioDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IInicoDAO;
import com.dawes.bibliotecavideojuegos.DTO.InicioDTO;
import org.springframework.stereotype.Service;

@Service
public class InicioService {

    private final IInicoDAO inicoDAO;

    public InicioService(IInicoDAO inicoDAO) {
        this.inicoDAO = inicoDAO;
    }

    public InicioDTO mapearInicio(){
        return inicoDAO.mapearInicio();
    }

}
