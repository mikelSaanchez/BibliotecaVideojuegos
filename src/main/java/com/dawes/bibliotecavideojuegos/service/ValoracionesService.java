package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.IPrestamoDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.ISocioDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IValoracionDAO;
import com.dawes.bibliotecavideojuegos.DAO.interfaz.IVideojuegoDAO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.DTO.ValoracionDTO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ValoracionesService {
    private final IValoracionDAO valoracionDAO;
    private final ISocioDAO socioDAO;
    private final IVideojuegoDAO videojuegoDAO;
    private final IPrestamoDAO prestamoDAO;

    public ValoracionesService(IValoracionDAO iValoracionDAO, ISocioDAO socioDAO, IVideojuegoDAO videojuegoDAO, IPrestamoDAO prestamoDAO) {
        this.valoracionDAO = iValoracionDAO;
        this.socioDAO = socioDAO;
        this.videojuegoDAO = videojuegoDAO;
        this.prestamoDAO = prestamoDAO;
    }

    public ArrayList<ValoracionDTO> filtrarValoraciones(Integer idVideojuego, Integer idSocio, Integer puntuacionMinima) {
        return valoracionDAO.buscar(idVideojuego, idSocio, puntuacionMinima);
    }

    public void borrar(int id) {
        ValoracionDTO valoracion = valoracionDAO.buscarPorId(id);
        if (valoracion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la valoracion con id " + id);
        }
        valoracionDAO.borrar(id);
    }

    public ValoracionDTO insertarValoracion(ValoracionDTO valoracion) {
        SocioDTO socio = socioDAO.obtenerSocioPorId(valoracion.getIdSocio());
        VideojuegoDTO videojuego = videojuegoDAO.obtenerVideojuegoPorId(valoracion.getIdVideojuego());

        if (socio == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el socio con id " + valoracion.getIdSocio());
        }
        if (videojuego == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el videojuego con id " + valoracion.getIdVideojuego());
        }
        if (valoracion.getPuntuacion() < 1 && valoracion.getPuntuacion() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " No está entre 1 y 5.");
        }
        if (socio.getAlta() == null || socio.getAlta() != 1) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "El socio debe estar activo.");
        }
        if (valoracionDAO.haDevueltoVideojuego(socio.getId(), videojuego.getId())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "El socio debe haber devuelto alguna copia de ese videojuego");
        }
        if (valoracionDAO.existeValoracion(socio.getId(), videojuego.getId())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Un socio solo puede valorar una vez cada videojuego");
        }

        LocalDate hoy = LocalDate.now();
        valoracion.setFecha(hoy);

        int filasAfectadas = valoracionDAO.insertar(valoracion);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido insertar el socio por un error inesperado. Inténtalo de nuevo.");
        }
        return valoracionDAO.buscarPorId(valoracion.getId());
    }


}



