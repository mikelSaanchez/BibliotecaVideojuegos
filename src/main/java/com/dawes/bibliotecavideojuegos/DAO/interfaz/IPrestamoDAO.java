package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.PrestamoDTO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IPrestamoDAO {
    ArrayList<DesplegableDTO> listarSociosActivos();
    ArrayList<DesplegableDTO> listarCopiasDisponibles();
    int insertarPrestamo(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite);
    SocioDTO obtenerSocioPorId(int idSocio);
    CopiaDTO obtenerCopia(int idCopia);
    boolean copiaDisponible(int idCopia);
    int contarPrestamosAbiertos(int idSocio);
    boolean tienePrestamoVencido(int idSocio, LocalDate hoy);

    ArrayList<PrestamoDTO> listarPrestamos(String socio, String titulo, LocalDate fechaDesde, boolean soloAbiertos);

    boolean devolverPrestamo(int idPrestamo, int idCopia, LocalDate fechaDevolucion, BigDecimal multa, boolean copiaDanada);

    int renovarPrestamo(int idPrestamo, LocalDate nuevaFechaLimite);
}
