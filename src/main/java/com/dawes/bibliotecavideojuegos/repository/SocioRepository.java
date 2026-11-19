package com.dawes.bibliotecavideojuegos.repository;

import com.dawes.bibliotecavideojuegos.DTO.SocioDTO;
import com.dawes.bibliotecavideojuegos.excepcion.AccesoDatosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class SocioRepository {
    private static final Logger logger = LoggerFactory.getLogger(SocioRepository.class);
    private final DataSource dataSource;

    public SocioRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<SocioDTO> filtarSocio(String nombre, String apellidos, String email, Integer activo) {
        StringBuilder sql = new StringBuilder("select s.id,s.nombre,s.apellidos, s.email, s.activo,s.fecha_alta,s.activo,TIMESTAMPDIFF(YEAR, s.fecha_nacimiento, CURDATE()) AS edad,\n" + "(select count(*) from prestamos p where p.id_socio = s.id AND p.fecha_devolucion IS NULL) as prestamosAbiertos \n " + "from socios s WHERE 1 = 1");

        ArrayList<Object> parametros = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            sql.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (apellidos != null && !apellidos.isBlank()) {
            sql.append(" AND apellidos LIKE ?");
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
            //System.out.println(sql);
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al filtrar videojuego", e);
            cerrarConexion(connection);
        }

        return listaSocios;
    }

    public int insertar(SocioDTO socio) {
        String sql = "insert into socios (nombre,apellidos,email,fecha_nacimiento,fecha_alta,activo) values (?,?,?,?,CURDATE(),1);";
        Connection connection = null;
        int filasAfectadas = 0;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setString(3, socio.getEmail());
            ps.setObject(4, socio.getFecha_nacimiento());

            filasAfectadas = ps.executeUpdate();

        } catch (SQLException e) {
            logger.info("Error al insertar el socio {}", socio.getId(), e);
            cerrarConexion(connection);
        }

        return filasAfectadas;
    }

    public SocioDTO obtenerSocioPorId(int id) {
        String sql = "SELECT id, nombre, apellidos, email, fecha_alta, fecha_nacimiento, activo FROM socios WHERE id = ?";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SocioDTO socio = new SocioDTO();
                socio.setId(rs.getInt("id"));
                socio.setNombre(rs.getString("nombre"));
                socio.setApellidos(rs.getString("apellidos"));
                socio.setEmail(rs.getString("email"));
                socio.setFecha_alta(rs.getDate("fecha_alta").toLocalDate());
                socio.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                socio.setAlta(rs.getInt("activo"));
                connection.close();
                return socio;
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Error al obtener socio {}", id, e);
            cerrarConexion(connection);
        }
        return null;
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) AS total FROM socios WHERE email = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al comprobar el email {}", email, e);
            return false;
        }
    }

    public void actualizar(SocioDTO s) {
        String sql = "UPDATE socios SET nombre = ?, apellidos = ?, email = ?, fecha_nacimiento = ?, activo = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getApellidos());
            ps.setString(3, s.getEmail());
            ps.setDate(4, Date.valueOf(s.getFecha_nacimiento()));
            ps.setInt(5, s.getAlta());
            ps.setInt(6, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al actualizar el socio {}", s.getId(), e);
            throw new AccesoDatosException("Error al actualizar el socio", e);
        }
    }

    public void borrar(int id) {
        String sql = "DELETE FROM socios WHERE id = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error al borrar el socio {}", id, e);
            throw new AccesoDatosException("Error al borrar el socio", e);
        }
    }

    public int contarPrestamos(int idSocio) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_socio = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            logger.error("Error al contar los préstamos del socio {}", idSocio, e);
            throw new AccesoDatosException("Error al contar los préstamos", e);
        }
    }

    private SocioDTO mapearSocio(ResultSet rs) throws SQLException {
        return new SocioDTO(rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("email"),
                rs.getInt("edad"),
                rs.getInt("activo"),
                rs.getInt("prestamosAbiertos"),
                rs.getDate("fecha_alta").toLocalDate()
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
