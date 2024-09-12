package project.utp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.utp.Config.JwtUtil;
import project.utp.modelo.JwtRequest;
import project.utp.modelo.JwtResponse;
import project.utp.modelo.Usuario;
import project.utp.service.detalleUsuarioService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")

public class authentticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private detalleUsuarioService detalleusuarioservice;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generartoken")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getEmail(), jwtRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("USUARIO NO ENCONTRADO");
        }

        UserDetails userDetails = detalleusuarioservice.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String email, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (DisabledException disabledException){
            throw new Exception("USUARIO DESHABILITADO" + disabledException.getMessage());
        } catch (BadCredentialsException badCredentialsException){
            throw new Exception("CREDENCIALES INVALIDAS" + badCredentialsException.getMessage());
        }

    }

    @GetMapping("/actualUser")
    public Usuario obtenerUserActual(Principal principal){
        return  (Usuario) detalleusuarioservice.loadUserByUsername(principal.getName());

       }
}

            /*
        String email = usuario.getEmail();
        String password = usuario.getPassword();

        // Cargar los detalles del usuario
        UserDetails userDetails = detalleusuarioservice.loadUserByUsername(email);

        // Verificar la contraseña usando BCryptPasswordEncoder
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            // Autenticación exitosa, generar el token
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(token);
        } else {
            // Credenciales inválidas
            return ResponseEntity.badRequest().body("Invalid credentials");
        }*/