package com.dawes.bibliotecavideojuegos.DAO.interfaz;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;


import java.util.ArrayList;

public interface ISocioDAO {

    ArrayList<SocioDTO> filtarSocio(String nombre, String apellido, String coreo, Integer activo);
    int insertarSocio (SocioDTO socio);
}
