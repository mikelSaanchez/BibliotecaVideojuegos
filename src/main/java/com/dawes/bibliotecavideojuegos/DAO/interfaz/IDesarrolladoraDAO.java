package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;

import java.util.ArrayList;

public interface IDesarrolladoraDAO {
    ArrayList<DesplegableDTO> listarParaDesplegable();
    ArrayList<DesarrolladoraDTO> listarTodas();
    boolean existePorId(int id);
}
