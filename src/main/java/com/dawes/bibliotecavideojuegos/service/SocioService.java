package com.dawes.bibliotecavideojuegos.service;

import com.dawes.bibliotecavideojuegos.DAO.interfaz.ISocioDAO;
import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.ReglaNegocioExcepcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class SocioService {
    private final ISocioDAO socioDAO;


    public SocioService(ISocioDAO socioDAO) {
        this.socioDAO = socioDAO;
    }

    public ArrayList<SocioDTO> filtarSocio(String nombre, String apellido, String coreo, Integer activo) {
        return socioDAO.filtarSocio(nombre, apellido, coreo, activo);
    }

    public void insertarSocio(SocioDTO socio) {

        if (socio.getNombre() == null || socio.getNombre().isBlank()) {
            throw new ReglaNegocioExcepcion("El nombre del socio es obligatorio.");
        }
        if (socio.getApellidos() == null || socio.getApellidos().isBlank()) {
            throw new ReglaNegocioExcepcion("El apellido del socio es obligatorio.");
        }
        if (socio.getEmail() == null || socio.getEmail().isBlank()) {
            throw new ReglaNegocioExcepcion("El email del socio es obligatorio.");
        }
        if (socio.getFecha_nacimiento() == null) {
            throw new ReglaNegocioExcepcion("El año del socio es obligatorio.");
        }
        if (socio.getFecha_nacimiento().isAfter(LocalDate.now())) {
            throw new ReglaNegocioExcepcion("El año del socio no puede ser mas alto que el de la fecha de hoy.");
        }

        int filasAfectadas = socioDAO.insertarSocio(socio);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido insertar el socio por un error inesperado. Inténtalo de nuevo.");
        }
    }
}
