package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IDesarrolladoraDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Service
public class VideojuegoService {
    private final IVideojuegoDAO videojuegoDAO;
    private final IDesarrolladoraDAO desarrolladoraDAO;
    private static final Set<Integer> edadesPEGI = Set.of(3, 7, 12, 16, 18);

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

    public void insertarVideojuego(VideojuegoDTO videojuego) {

        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank()) {
            throw new ReglaNegocioExcepcion("El título del videojuego es obligatorio.");
        }
        if (videojuego.getAnio_lanzamiento() == null) {
            throw new ReglaNegocioExcepcion("El año de lanzamiento del videojuego es obligatorio.");
        }
        if (videojuego.getAnio_lanzamiento() < 1970 || videojuego.getAnio_lanzamiento() > LocalDate.now().getYear()) {
            throw new ReglaNegocioExcepcion("El año de lanzamiento debe estar entre 1970 y el año actual.");
        }

        if (!edadesPEGI.contains(videojuego.getPegi())) {
            throw new ReglaNegocioExcepcion("El PEGI debe ser 3, 7, 12, 16 o 18.");
        }

        int filasAfectadas = videojuegoDAO.insertarVideojuego(videojuego);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido insertar el videojuego por un error inesperado. Inténtalo de nuevo.");
        }
    }

    public ArrayList<VideojuegoDTO> VideoJuegosMasPrestados() {
        return videojuegoDAO.VideoJuegosMasPrestados();
    }

}
