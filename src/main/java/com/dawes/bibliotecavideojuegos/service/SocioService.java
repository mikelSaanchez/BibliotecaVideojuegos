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
        if (socioDAO.existeEmail(socio.getEmail().trim())) {
            throw new ReglaNegocioExcepcion("El email ya está registrado. Introduce otro email.");
        }

        socio.setEmail(socio.getEmail().trim());

        int filasAfectadas = socioDAO.insertarSocio(socio);

        if (filasAfectadas != 1) {
            throw new ReglaNegocioExcepcion("No se ha podido insertar el socio por un error inesperado. Inténtalo de nuevo.");
        }
    }

    public SocioDTO actualizar(int id, SocioDTO socio) {
        SocioDTO actual = obtener(id); // 404 si no existe
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
        if (socioDAO.existeEmail(socio.getEmail().trim())) {
            throw new ReglaNegocioExcepcion("El email ya está registrado. Introduce otro email.");
        }
        String email = socio.getEmail().trim();
        if (!actual.getEmail().equalsIgnoreCase(socio.getEmail()) && socioDAO.existeEmail(email)) {
            throw new ReglaNegocioExcepcion("Ya existe otro socio con el email " + email);
        }
        Integer activo = actual.getAlta();
        if (socio.getAlta() != null) activo = socio.getAlta();
        socioDAO.actualizar(new SocioDTO(id, socio.getNombre().trim(), socio.getApellidos().trim(), email, socio.getFecha_nacimiento(), actual.getFecha_alta(), activo, 0, 0));
        return obtener(id);
    }

    public void borrar(int id) {
        obtener(id); // 404 si no existe
        int prestamos = socioDAO.contarPrestamos(id);
        if (prestamos > 0) {
            throw new ReglaNegocioExcepcion("No se puede borrar el socio: tiene " + prestamos + " préstamo(s) registrados. Dalo de baja en su lugar (activo = false)");
        }
        socioDAO.borrar(id);
    }

    public SocioDTO obtener(int id) {
        SocioDTO socio = socioDAO.obtenerSocioPorId(id);
        if (socio == null) {
            throw new ReglaNegocioExcepcion("No existe el socio con id " + id);
        }
        return socio;
    }
}
