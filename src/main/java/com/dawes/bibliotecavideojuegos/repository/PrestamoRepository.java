package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.CopiaDTO;
import com.dawes.bibliotecavideojuegos.DTO.DesplegableDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class PrestamoRepository {
    private static final Logger logger = LoggerFactory.getLogger(PrestamoRepository.class);
    private final DataSource dataSource;

    public PrestamoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<DesplegableDTO> listarSociosActivos() {
        String sql = "SELECT id, CONCAT(nombre, ' ', apellidos) AS nombre FROM socios WHERE activo = 1 ORDER BY apellidos, nombre";
        ArrayList<DesplegableDTO> lista = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new DesplegableDTO(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            logger.error("Error al listar los socios activos", e);
        }
        return lista;
    }

    public ArrayList<DesplegableDTO> listarCopiasDisponibles() {
        String sql = "SELECT c.id, CONCAT(v.titulo, ' (', pl.nombre, ') - ', c.codigo_inventario) AS nombre " +
                "FROM copias c JOIN videojuegos v ON v.id = c.id_videojuego JOIN plataformas pl ON pl.id = c.id_plataforma " +
                "WHERE c.activa = 1 AND NOT EXISTS (SELECT 1 FROM prestamos p WHERE p.id_copia = c.id AND p.fecha_devolucion IS NULL) " +
                "ORDER BY v.titulo, pl.nombre, c.codigo_inventario";
        ArrayList<DesplegableDTO> lista = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new DesplegableDTO(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            logger.error("Error al listar las copias disponibles", e);
        }
        return lista;
    }

    public int insertarPrestamo(int idSocio, int idCopia, LocalDate fechaPrestamo, LocalDate fechaLimite) {
        String sql = "INSERT INTO prestamos (id_socio, id_copia, fecha_prestamo, fecha_limite, fecha_devolucion, multa) VALUES (?, ?, ?, ?, NULL, 0)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setInt(2, idCopia);
            ps.setDate(3, Date.valueOf(fechaPrestamo));
            ps.setDate(4, Date.valueOf(fechaLimite));
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al insertar el préstamo", e);
            return 0;
        }
    }

    public CopiaDTO obtenerCopia(int idCopia) {
        String sql = "SELECT c.id, c.codigo_inventario, c.activa, v.titulo, v.anio_lanzamiento, v.pegi, pl.nombre AS plataforma " + "FROM copias c JOIN videojuegos v ON v.id = c.id_videojuego JOIN plataformas pl ON pl.id = c.id_plataforma WHERE c.id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCopia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CopiaDTO copia = new CopiaDTO();
                    copia.setId(rs.getInt("id"));
                    copia.setCodigoInventario(rs.getString("codigo_inventario"));
                    copia.setActiva(rs.getBoolean("activa"));
                    copia.setTitulo(rs.getString("titulo"));
                    copia.setAnioLanzamiento(rs.getInt("anio_lanzamiento"));
                    copia.setPegi(rs.getInt("pegi"));
                    copia.setPlataforma(rs.getString("plataforma"));
                    return copia;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener la copia {}", idCopia, e);
        }
        return null;
    }

    public boolean copiaDisponible(int idCopia) {
        String sql = "SELECT c.activa AND NOT EXISTS (SELECT 1 FROM prestamos p WHERE p.id_copia = c.id AND p.fecha_devolucion IS NULL) AS disponible FROM copias c WHERE c.id = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idCopia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean("disponible");
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar la disponibilidad de la copia {}", idCopia, e);
            return false;
        }
    }

    public int contarPrestamosAbiertos(int idSocio) {
        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE id_socio = ? AND fecha_devolucion IS NULL";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("total") : 0;
            }
        } catch (SQLException e) {
            logger.error("Error al contar los préstamos abiertos del socio {}", idSocio, e);
            return 0;
        }
    }

    public boolean tienePrestamoVencido(int idSocio, LocalDate hoy) {
        String sql = "SELECT COUNT(*) AS total FROM prestamos WHERE id_socio = ? AND fecha_devolucion IS NULL AND fecha_limite < ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setDate(2, Date.valueOf(hoy));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar préstamos vencidos del socio {}", idSocio, e);
            return false;
        }
    }


}
