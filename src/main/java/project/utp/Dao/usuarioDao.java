package project.utp.Dao;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.utp.modelo.Rol;
import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;

import javax.security.auth.login.LoginException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class usuarioDao implements Iusuario {


    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Usuario> listarUsuario() {
        String sqlUsuarios = "SELECT * FROM usuario";
        List<Usuario> usuarios = jdbcTemplate.query(sqlUsuarios, new BeanPropertyRowMapper<>(Usuario.class));

        for (Usuario usuario : usuarios) {
            String sqlUsuarioRol = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.rol_id WHERE ur.usuario_id = ?";
            List<Rol> roles = jdbcTemplate.query(sqlUsuarioRol, new Object[]{usuario.getId_usuario()}, new BeanPropertyRowMapper<>(Rol.class));

            Set<UsuarioRol> usuarioRoles = new HashSet<>();
            for (Rol rol : roles) {
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(usuario);
                usuarioRol.setRol(rol);
                usuarioRoles.add(usuarioRol);
            }

            usuario.setUsuarioRoles(usuarioRoles);
        }
        return usuarios;
    }

    @Override
    public Usuario registraruser(Usuario usuario) {
        // Registrar usuario en la tabla usuario
        String sqlUsuario = "INSERT INTO usuario (nombre, apellido, email, password, telefono) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlUsuario, usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getPassword(), usuario.getTelefono());

        // Obtener el id del usuario recién registrado
        long id_usuario = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Asignar el rol por defecto (2) al usuario en la tabla usuario_rol
        String sqlUsuarioRolDefault = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (LAST_INSERT_ID(), 2)";
        jdbcTemplate.update(sqlUsuarioRolDefault);

        // Agregar el rol por defecto al objeto Usuario
        Rol rolDefault = new Rol();
        rolDefault.setId_rol(2l);
        rolDefault.setRol_nombre("USER");// Asignar el nombre del rol según sea necesario

        UsuarioRol usuarioRolDefault = new UsuarioRol();
        usuarioRolDefault.setRol(rolDefault);
        usuarioRolDefault.setUsuario(usuario);
        usuario.getUsuarioRoles().add(usuarioRolDefault);

        // Devolver el usuario registrado con sus roles
        usuario.setId_usuario(id_usuario);
        return usuario;
    }




    public Usuario obtenerEmail( String email){
        try {
            String sql = "SELECT * FROM usuario WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{email}, new usuarioMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean obtenerPassword(String password){
        String sql="Select password From usuario WHERE estado = 1";

        Usuario pass = jdbcTemplate.queryForObject(sql, new usuarioMapper(),password);

        boolean authPass = (pass != null) ? true : false;

        return authPass;
    }





/*
    @Override
    public Usuario registrarUsuario(Usuario objeto) {
            // Insertamos el usuario en nuestra tabla usuario
            String sql = "INSERT INTO usuario (nombre, apellido,email, password, telefono) VALUES (?, ?, ?, ?, ?)";
            int filasAfectadas = jdbcTemplate.update(sql, objeto.getNombre(), objeto.getApellido(),
                    objeto.getEmail(), objeto.getPassword(), objeto.getTelefono());

            // Si el registro fue correcto, asignamos el rol usuario por defecto
            if (filasAfectadas > 0) {
                // Obtenemos el id del usuario recién insertado
                String sqlId = "SELECT LAST_INSERT_ID()";
                long userid = jdbcTemplate.queryForObject(sqlId, Long.class);

                // Buscamos el usuario en la base de datos
                String sqlUsuario = "SELECT * FROM usuario WHERE id_usuario = ?";
                Usuario usuario = jdbcTemplate.queryForObject(sqlUsuario, new usuarioMapper(), userid);

                // Buscamos el rol en la base de datos
                String sqlRol = "SELECT * FROM rol WHERE id_rol = ?";
                Rol rol = jdbcTemplate.queryForObject(sqlRol, new rolMapper(), 2);
                // Creamos un nuevo objeto UsuarioRol
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(usuario);
                usuarioRol.setRol(rol);

                // Agregamos el objeto UsuarioRol a la colección usuarioRoles del usuario
                usuario.getUsuarioRoles().add(usuarioRol);

                // Insertamos el objeto UsuarioRol en la tabla usuario_rol
                String sqlRolUser = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (?, ?)";
                Object[] params = new Object[]{userid, 2};
                jdbcTemplate.update(sqlRolUser, params);

                return usuario;
            } else {
                throw new RuntimeException("Error al registrar usuario");
            }
        }*/


    /*
    @Override
    public int login(Usuario usuario) {

        String sql = "SELECT * FROM usuario WHERE email = ?";
        //System.out.println(jdbcTemplate.queryForObject(sql,new Object[]{usuario.getEmail()},new usuarioMapper()));
        // List<Usuario> usuarios = jdbcTemplate.query(sql, new Object[]{username, password}, new usuarioMapper());
       // return jdbcTemplate.update(sql, usuario.getEmail());
       int email = jdbcTemplate.queryForObject(sql, Integer.class,usuario.getEmail());

       if (email == 0){
        return  "correo electronico incorrecto";
       }
        return 1;

    }*/

    @Override
    public int  actualizarUsuario(Usuario objeto) {
        String sql = "UPDATE usuario SET username = ?, password = ?, nombre = ?, apellido = ?, email = ?, telefono = ? WHERE id_usuario = ?";
        return jdbcTemplate.update(sql, objeto.getUsername(), objeto.getPassword(), objeto.getNombre(), objeto.getApellido(), objeto.getEmail(), objeto.getTelefono());

    }

    @Override
    public Usuario buscarporid(long id_usuario) {
        String sqlUsuario = "SELECT * FROM usuario WHERE id_usuario = ?";
        Usuario usuario = jdbcTemplate.queryForObject(sqlUsuario, new Object[]{id_usuario}, new BeanPropertyRowMapper<>(Usuario.class));

        if (usuario != null) {
            String sqlUsuarioRol = "SELECT r.* FROM rol r INNER JOIN usuario_rol ur ON r.id_rol = ur.rol_id WHERE ur.usuario_id = ?";
            List<Rol> roles = jdbcTemplate.query(sqlUsuarioRol, new Object[]{id_usuario}, new BeanPropertyRowMapper<>(Rol.class));

            Set<UsuarioRol> usuarioRoles = new HashSet<>();
            for (Rol rol : roles) {
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(usuario);
                usuarioRol.setRol(rol);
                usuarioRoles.add(usuarioRol);
            }

            usuario.setUsuarioRoles(usuarioRoles);
        }

        return usuario;
    }
    @Override
    public int eliminarUsuario(long id_usuario) {
        String sql = "UPDATE usaurio SET estado = 0 WHERE id_usuario = ? And estado = 1";
        return jdbcTemplate.update(sql, id_usuario);
    }


    private static final class usuarioMapper implements RowMapper<Usuario>{

        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getLong("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTelefono(rs.getString("telefono"));
            usuario.setEstado(rs.getBoolean("estado"));

            return usuario;
        }
    }
    private static final class rolMapper implements RowMapper<Rol> {

        @Override
        public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rol rol = new Rol();
            rol.setId_rol(rs.getLong("id_rol"));
            rol.setRol_nombre(rs.getString("rol_nombre"));
            return rol;
        }
    }
}
