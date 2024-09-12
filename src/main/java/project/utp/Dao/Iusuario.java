package project.utp.Dao;

import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Iusuario{

    List<Usuario> listarUsuario();
    //Usuario registrarUsuario(Usuario objeto);
    Usuario registraruser(Usuario usuario);
    Usuario obtenerEmail( String email);
    int actualizarUsuario(Usuario objeto);
    Usuario buscarporid(long id_usuario);
    int eliminarUsuario(long id_usuario);

}
