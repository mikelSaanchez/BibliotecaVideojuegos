package com.dawes.bibliotecavideojuegos.repository;


import com.dawes.bibliotecavideojuegos.DTO.ValoracionDTO;
import com.dawes.bibliotecavideojuegos.excepcion.AccesoDatosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class ValoracionesRepository {
    private static final Logger logger = LoggerFactory.getLogger(ValoracionesRepository.class);

    private final DataSource dataSource;

    public ValoracionesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //BUSCAR CON FILTROS
    public ArrayList<ValoracionDTO> buscar(Integer idVideojuego, Integer idSocio, Integer puntuacionMinima) {
        String SQL_BASE = "SELECT v.id, v.id_socio, CONCAT(s.nombre, ' ', s.apellidos) AS nombre_socio, "
                + "v.id_videojuego, vj.titulo, v.puntuacion, v.comentario, v.fecha "
                + "FROM valoraciones v "
                + "JOIN socios s ON v.id_socio = s.id "
                + "JOIN videojuegos vj ON v.id_videojuego = vj.id";

        StringBuilder sql = new StringBuilder(SQL_BASE).append(" WHERE 1 = 1");
        ArrayList<Object> params = new ArrayList<>();

        if (idVideojuego != null) {
            sql.append(" AND v.id_videojuego = ?");
            params.add(idVideojuego);
        }
        if (idSocio != null) {
            sql.append(" AND v.id_socio = ?");
            params.add(idSocio);
        }
        if (puntuacionMinima != null) {
            sql.append(" AND v.puntuacion >= ?");
            params.add(puntuacionMinima);
        }
        // La más reciente primero; el id desempata las del mismo día
        sql.append(" ORDER BY v.fecha DESC, v.id DESC");

        ArrayList<ValoracionDTO> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar valoraciones", e);
            throw new AccesoDatosException("Error al buscar valoraciones", e);
        }
        return lista;
    }

    //BUSCAR POR ID
    public ValoracionDTO buscarPorId(int id) {
        String sql = "SELECT v.id, v.id_socio, CONCAT(s.nombre, ' ', s.apellidos) AS nombre_socio, "
                + "v.id_videojuego, vj.titulo, v.puntuacion, v.comentario, v.fecha "
                + "FROM valoraciones v "
                + "JOIN socios s ON v.id_socio = s.id "
                + "JOIN videojuegos vj ON v.id_videojuego = vj.id"
                + " WHERE v.id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al buscar la valoración {}", id, e);
            throw new AccesoDatosException("Error al buscar la valoración", e);
        }
    }

    /** RN-V2: ¿ha devuelto el socio alguna copia de ese videojuego? (préstamo con fecha_devolucion no nula) */
    public boolean haDevueltoVideojuego(int idSocio, int idVideojuego) {
        String sql = "SELECT COUNT(*) FROM prestamos p "
                + "JOIN copias c ON p.id_copia = c.id "
                + "WHERE p.id_socio = ? AND c.id_videojuego = ? AND p.fecha_devolucion IS NOT NULL";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setInt(2, idVideojuego);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar las devoluciones del socio {} del videojuego {}", idSocio, idVideojuego, e);
            throw new AccesoDatosException("Error al comprobar las devoluciones", e);
        }
    }

    /** RN-V3: ¿ya ha valorado este socio ese videojuego? */
    public boolean existeValoracion(int idSocio, int idVideojuego) {
        String sql = "SELECT COUNT(*) FROM valoraciones WHERE id_socio = ? AND id_videojuego = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setInt(2, idVideojuego);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar la valoración del socio {} al videojuego {}", idSocio, idVideojuego, e);
            throw new AccesoDatosException("Error al comprobar la valoración", e);
        }
    }

    /** Devuelve el id generado. La fecha (hoy) y el comentario ya vienen preparados desde el Service. */
    public int insertar(ValoracionDTO v) {
        String sql = "INSERT INTO valoraciones (id_socio, id_videojuego, puntuacion, comentario, fecha) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getIdSocio());
            ps.setInt(2, v.getIdVideojuego());
            ps.setDouble(3, v.getPuntuacion());
            ps.setString(4, v.getComentario());
            ps.setDate(5, Date.valueOf(v.getFecha()));

            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al insertar la valoración", e);
            throw new AccesoDatosException("Error al insertar la valoración", e);
        }
    }

    //BORRAR
    public void borrar(int id) {
        String sql = "DELETE FROM valoraciones WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al borrar la valoración {}", id, e);
            throw new AccesoDatosException("Error al borrar la valoración", e);
        }
    }


    // Metodo auxiliar para mapear
    private ValoracionDTO mapear(ResultSet rs) throws SQLException {
        return new ValoracionDTO(
                rs.getInt("id"),
                rs.getInt("id_socio"),
                rs.getString("nombre_socio"),
                rs.getInt("id_videojuego"),
                rs.getString("titulo"),
                rs.getDouble("puntuacion"),
                rs.getString("comentario"), // null si la columna es NULL
                rs.getDate("fecha").toLocalDate());
    }

    // Metodo auxiliar para setear los param
    public void setParams(PreparedStatement ps, ArrayList<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }
}
