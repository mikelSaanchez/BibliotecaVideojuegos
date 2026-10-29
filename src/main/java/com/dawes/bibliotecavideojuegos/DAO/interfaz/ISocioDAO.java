package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;

import java.util.ArrayList;

public interface ISocioDAO {

    ArrayList<SocioDTO> filtarSocio(String nombre, String apellido, String coreo, Integer activo);
}
