package com.dawes.bibliotecavideojuegos.DAO.impl;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.repository.DesarrolladoraRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class DesarrolladoraDAO implements IDesarrolladoraDAO {
    private final DesarrolladoraRepository desarrolladoraRepository;
    public DesarrolladoraDAO(DesarrolladoraRepository desarrolladoraRepository) {
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    @Override
    public ArrayList<DesplegableDTO> listarParaDesplegable() {
        return desarrolladoraRepository.listarParaDesplegable();
    }
}
