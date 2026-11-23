package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.DesarrolladoraDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
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
public class DesarrolladoraRepository {
    private static final Logger logger = LoggerFactory.getLogger(DesarrolladoraRepository.class);
    private final DataSource dataSource;

    public DesarrolladoraRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<DesplegableDTO> listarParaDesplegable() {
        String sql = "SELECT id, nombre FROM desarrolladoras ORDER BY nombre";
        ArrayList<DesplegableDTO> listaDesarrolladora = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaDesarrolladora.add(new DesplegableDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"))
                );
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al recuperar el desplegable de desarrolladoras", e);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return listaDesarrolladora;
    }

    public ArrayList<DesarrolladoraDTO> listarTodas() {
        String sql = "SELECT id, nombre, pais FROM desarrolladoras ORDER BY nombre";
        ArrayList<DesarrolladoraDTO> lista = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new DesarrolladoraDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("pais")));
            }
        } catch (SQLException e) {
            logger.error("Error al listar desarrolladoras", e);
        }
        return lista;
    }

    public boolean existePorId(int id) {
        String sql = "SELECT COUNT(*) AS total FROM desarrolladoras WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar la desarrolladora {}", id, e);
            return false;
        }
    }
}