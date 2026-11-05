package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.InicioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class InicioRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);
    private final DataSource dataSource;

    public InicioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int contarVideoJuegos() {
        String sql = "select count(id) AS contador from videojuegos;";
        int contador = 0;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("contador");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }
        return contador;
    }

    public int sociosActivos() {
        String sql = "SELECT count(*) AS contador FROM socios where activo = 1;";
        int contador = 0;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("contador");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }
        return contador;
    }

    public int prestamosAbiertos() {
        String sql = "SELECT COUNT(*) AS contador FROM prestamos WHERE fecha_devolucion IS NULL";
        int contador = 0;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("contador");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }
        return contador;
    }

    public int prestamosVencidos() {
        String sql = "SELECT COUNT(*) AS contador FROM prestamos WHERE fecha_devolucion IS NULL AND fecha_limite < CURDATE();";
        int contador = 0;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contador = rs.getInt("contador");
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }
        return contador;
    }


    public InicioDTO mapearInicio() {
        return new InicioDTO(contarVideoJuegos(),
                sociosActivos(),
                prestamosAbiertos(),
                prestamosVencidos());
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
