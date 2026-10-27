package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;

import java.util.ArrayList;

public interface IVideojuegoDAO {

    ArrayList<VideojuegoDTO> filtarVideojuego(String titulo, Integer id_desarrolladora, Integer anio_lanzamiento, Integer pegi, Boolean activo);
    int insertarVideojuego (VideojuegoDTO videojuego);
}
