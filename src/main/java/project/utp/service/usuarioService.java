package project.utp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import project.utp.Config.Validacion;
import project.utp.Dao.usuarioDao;
import project.utp.modelo.Usuario;

import java.util.List;

@Service
public class    usuarioService {

    @Autowired
    private usuarioDao userDao;

    @Autowired
    private Validacion validacion;

    public List<Usuario> listarusuario(){return userDao.listarUsuario();}

    public Usuario registrarUsuario(Usuario usuario ) {

        //validamos si el email ya existe
        Usuario emailexiste = userDao.obtenerEmail(usuario.getEmail());
        if (emailexiste != null) {
            throw new IllegalArgumentException("El correo electronico ya esta en uso");

        }
        // validamos el formato del email
        if (!validacion.validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }

        //validar formato de contraseña
        if (!validacion.validarPassword(usuario.getPassword())) {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos minimos");
        }
        //encriptamos la contraseña
        String encriptarcontraseña = validacion.encriptarPassword(usuario.getPassword());
        usuario.setPassword(encriptarcontraseña);
         return userDao.registraruser(usuario);

    }




/*
        //validamos si el email ya existe
        Usuario emailexiste = userDao.obtenerEmail(usuario.getEmail());
        if (emailexiste != null) {
            throw new IllegalArgumentException("El correo electronico ya esta en uso");

        }
        // validamos el formato del email
       if (!validacion.validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email no tiene un formato valido");
        }

        //validar formato de contraseña
          if (!validacion.validarPassword(usuario.getPassword())) {
           throw new IllegalArgumentException("La contraseña no cumple con los requisitos minimos");
        }
            //encriptamos la contraseña
          String encriptarcontraseña = validacion.encriptarPassword(usuario.getPassword());
           usuario.setPassword(encriptarcontraseña);

        //registramos el usuario
        return userDao.registrarUsuario(usuario);
    }
*/




}
