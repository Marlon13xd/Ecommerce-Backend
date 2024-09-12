package project.utp.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.utp.Dao.usuarioDao;
import project.utp.modelo.Rol;
import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class detalleUsuarioService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private usuarioDao usuaridao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Buscando usuario con email: " + email);

        Usuario usuario = usuaridao.obtenerEmail(email);
        if (usuario == null) {
            System.out.println("Usuario no encontrado con email: " + email);
            throw new UsernameNotFoundException("usuario no encontrado");
        }

        // cargamos el usuarioroles
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

        return usuario;

    }
}
