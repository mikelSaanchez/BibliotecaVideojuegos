package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.repository.VideojuegoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public class VideojuegoDAO implements IVideojuegoDAO {
private final VideojuegoRepository videojuegoRepository;

    public VideojuegoDAO(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    @Override
    public ArrayList<VideojuegoDTO> filtarVideojuego(String titulo, Integer id_desarrolladora, Integer anio_lanzamiento, Integer pegi, Boolean activo) {
        return videojuegoRepository.filtarVideojuego(titulo, id_desarrolladora, anio_lanzamiento, pegi, activo);
    }
    @Override
    public int insertarVideojuego(VideojuegoDTO videojuego) {
        return videojuegoRepository.insertar(videojuego);
    }
}
