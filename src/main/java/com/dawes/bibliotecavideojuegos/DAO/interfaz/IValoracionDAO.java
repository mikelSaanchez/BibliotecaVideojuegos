package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.ValoracionDTO;

import java.util.ArrayList;

public interface IValoracionDAO {
    ArrayList<ValoracionDTO> buscar(Integer idVideojuego, Integer idSocio, Integer puntuacionMinima);
    ValoracionDTO buscarPorId(int id);
    boolean haDevueltoVideojuego(int idSocio, int idVideojuego);
    boolean existeValoracion(int idSocio, int idVideojuego);
    int insertar(ValoracionDTO v);
    void borrar(int id);


}
