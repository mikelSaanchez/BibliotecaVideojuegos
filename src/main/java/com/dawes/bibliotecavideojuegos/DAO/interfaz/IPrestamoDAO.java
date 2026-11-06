package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IPrestamoDAO {
    ArrayList<DesplegableDTO> listarSociosActivos();
    ArrayList<DesplegableDTO> listarCopiasDisponibles();
    int insertarPrestamo(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite);
    SocioDTO obtenerSocioPorId(int idSocio);
}
