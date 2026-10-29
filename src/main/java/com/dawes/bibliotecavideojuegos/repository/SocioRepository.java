package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class SocioRepository {
    private static final Logger logger = LoggerFactory.getLogger(SocioRepository.class);
    private final DataSource dataSource;

    public SocioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<SocioDTO> filtarSocio(String nombre, String apellidos, String email, Integer activo) {
        StringBuilder sql = new StringBuilder("SELECT s.id, s.nombre, s.apellidos, s.email, s.activo,s.fecha_alta,TIMESTAMPDIFF(YEAR, s.fecha_nacimiento, CURDATE()) AS edad, COUNT(p.id_socio) AS prestamosAbiertos FROM socios s JOIN prestamos p ON s.id = p.id_socio WHERE 1 = 1");
        ArrayList<Object> parametros = new ArrayList<>();

        if ((nombre != null && !nombre.isBlank()) && (apellidos != null && !apellidos.isBlank())) {
            sql.append(" AND nombre LIKE ? AND apellidos like ?");
            parametros.add("%" + nombre + "%");
            parametros.add("%" + apellidos + "%");
        }

        if (email != null && !email.isBlank()) {
            sql.append(" AND email LIKE ?");
            parametros.add("%" + email + "%");
        }
        if (activo != null) {
            sql.append(" AND activo = ?");
            parametros.add(activo);
        }
        sql.append(" group by p.id_socio;");

        ArrayList<SocioDTO> listaSocios = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaSocios.add(mapearSocio(rs));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }

        return listaSocios;
    }

    private SocioDTO mapearSocio(ResultSet rs) throws SQLException {
        return new SocioDTO(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getInt("edad"),
                rs.getInt("activo"),
                rs.getInt("prestamosAbiertos"),
                rs.getDate("fecha_alta").toLocalDate());
    }

    private void cerrarConexion(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}

