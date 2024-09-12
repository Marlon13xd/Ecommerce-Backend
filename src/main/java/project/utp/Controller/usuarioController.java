package project.utp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;
import project.utp.service.detalleUsuarioService;
import project.utp.service.usuarioService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class usuarioController {

    @Autowired
    private usuarioService usercontroller;


    @Autowired
    private detalleUsuarioService detalleusuarioservice;


    @GetMapping("/listar")
    public List<Usuario> listarUusario() {
        return usercontroller.listarusuario();
    }

    @PostMapping
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usercontroller.registrarUsuario(usuario);
    }

}


