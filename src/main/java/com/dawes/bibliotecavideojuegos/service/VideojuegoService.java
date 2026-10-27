package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VideojuegoService {
    private final IVideojuegoDAO videojuegoDAO;
    private final IDesarrolladoraDAO desarrolladoraDAO;

    public VideojuegoService(IVideojuegoDAO videojuegoDAO, IDesarrolladoraDAO desarrolladoraDAO) {
        this.videojuegoDAO = videojuegoDAO;
        this.desarrolladoraDAO = desarrolladoraDAO;
    }

    public ArrayList<DesplegableDTO> obtenerDesplegableDesarrolladoras() {
        return desarrolladoraDAO.listarParaDesplegable();
    }

    public ArrayList<VideojuegoDTO> filtrarVideojuego(String titulo, Integer id_desarrolladora, Integer anio_lanzamiento, Integer pegi, Boolean activo) {
        return videojuegoDAO.filtarVideojuego(titulo, id_desarrolladora, anio_lanzamiento, pegi, activo);
    }
}
