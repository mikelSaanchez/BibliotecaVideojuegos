package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IValoracionDAO;
import com.dawes.bibliotecavideojuegos.DTO.ValoracionDTO;
import com.dawes.bibliotecavideojuegos.repository.ValoracionesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ValoracionDAO implements IValoracionDAO {
    private final ValoracionesRepository valoracionesRepository;

    public ValoracionDAO(ValoracionesRepository valoracionesRepository) {
        this.valoracionesRepository = valoracionesRepository;
    }

    @Override
    public ArrayList<ValoracionDTO> buscar(Integer idVideojuego, Integer idSocio, Integer puntuacionMinima) {
        return valoracionesRepository.buscar(idVideojuego, idSocio, puntuacionMinima);
    }

    @Override
    public ValoracionDTO buscarPorId(int id) {
        return valoracionesRepository.buscarPorId(id);
    }

    @Override
    public boolean haDevueltoVideojuego(int idSocio, int idVideojuego) {
        return valoracionesRepository.haDevueltoVideojuego(idSocio, idVideojuego);
    }

    @Override
    public boolean existeValoracion(int idSocio, int idVideojuego) {
        return valoracionesRepository.existeValoracion(idSocio, idVideojuego);
    }

    @Override
    public int insertar(ValoracionDTO v) {
        return valoracionesRepository.insertar(v);
    }

    @Override
    public void borrar(int id) {
        valoracionesRepository.borrar(id);
    }
}
