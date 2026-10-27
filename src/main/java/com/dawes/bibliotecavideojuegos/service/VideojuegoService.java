package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void insertarVideojuego(VideojuegoDTO videojuego){

        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()) {
            throw new ReglaNegocioExcepcion("El nombre del videojuego es obligatorio.");
        }
        if (videojuego.getAnio_lanzamiento() == null) {
            throw new ReglaNegocioExcepcion("El anio de lanzamiento del videojuego es obligatorio.");
        }
        if (videojuego.getAnio_lanzamiento() > 1970 && videojuego.getAnio_lanzamiento() < LocalDate.now().getYear()) {
            throw new ReglaNegocioExcepcion("El anio de lanzamiento del videojuego tiene que estar entre los valores solicitados.");
        }

        if (videojuego.getPegi() == 3) {
            throw new ReglaNegocioExcepcion("El anio de lanzamiento del videojuego es obligatorio.");
        }

        int filasAfectadas = videojuegoDAO.insertarVideojuego(videojuego);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido insertar el videojuego por un error inesperado. Inténtalo de nuevo.");
        }
    }
}
