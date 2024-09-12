package project.utp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.utp.Dao.Iusuario;
import project.utp.modelo.Rol;
import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication

public class UtpApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UtpApplication.class, args);
	}

	@Autowired
	private Iusuario iusuario;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void run(String... args) throws Exception {


	/*	Usuario usuario = new Usuario();

		usuario.setNombre("Christian");
		usuario.setApellido("Ramirez");
		usuario.setPassword(bCryptPasswordEncoder.encode("123456789"));
		usuario.setEmail("c123456@gmail.com");
		usuario.setTelefono("988212020");



		Rol rol = new Rol();
		rol.setId_rol(1L);
		rol.setRol_nombre("ADMIN");

		Set<UsuarioRol> usuariosRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuariosRoles.add(usuarioRol);

		Usuario usuariog = iusuario.registraruser(usuario);
		System.out.println(usuariog.getNombre());

	} */
	}
}

