package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.VideojuegoDTO;
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
public class VideojuegoRepository {
    private static final Logger logger = LoggerFactory.getLogger(VideojuegoRepository.class);
    private final DataSource dataSource;

    public VideojuegoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<VideojuegoDTO> filtarVideojuego(String titulo, Integer id_desarrolladora, Integer anio_lanzamiento, Integer pegi, Boolean copias_disponibles) {
        StringBuilder sql = new StringBuilder("SELECT id, titulo,desarrolladora, anio_lanzamiento,pegi, copias_totales,copias_disponibles FROM biblioteca.v_videojuegos_detalle WHERE 1=1");

        ArrayList<Object> parametros = new ArrayList<>();

        if (titulo != null && !titulo.isBlank()) {
            sql.append(" AND titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (id_desarrolladora != null) {
            sql.append(" AND id_desarrolladora = ?");
            parametros.add(id_desarrolladora);
        }
        if (anio_lanzamiento != null) {
            sql.append(" AND anio_lanzamiento >= ?");
            parametros.add(anio_lanzamiento);
        }
        if (pegi != null) {
            sql.append(" AND pegi <= ?");
            parametros.add(pegi);
        }
        if (copias_disponibles != null) {
            sql.append(" AND copias_disponibles = ?");
            parametros.add(copias_disponibles);
        }
        ArrayList<VideojuegoDTO> listaVideojuego = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                listaVideojuego.add(mapearVideojuego(rs));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }
        return listaVideojuego;
    }
    public int insertar(VideojuegoDTO videojuego){
        String sql = "INSERT INTO videojuegos (titulo, anio_lanzamiento,pegi, id_desarrolladora) VALUES (?, ?, ?, ?);";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, videojuego.getTitulo());
            ps.setInt(2, videojuego.getAnio_lanzamiento());
            ps.setInt(3, videojuego.getPegi());
            ps.setInt(4, videojuego.getId_desarrolladora());

            filasAfectadas = ps.executeUpdate();

        } catch (SQLException e) {
            logger.info("Error al insertar el alumno {}", videojuego.getId(), e);
            cerrarConexion(connection);
        }
        return filasAfectadas;
    }

    private VideojuegoDTO mapearVideojuego(ResultSet rs) throws SQLException {
        return new VideojuegoDTO(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("desarrolladora"),
                rs.getInt("anio_lanzamiento"),
                rs.getInt("pegi"),
                rs.getInt("copias_totales"),
                rs.getInt("copias_disponibles")
        );
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
