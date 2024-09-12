package project.utp.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Validacion {

    private   BCryptPasswordEncoder passwordEncoder;

    // Método para validar email
    public boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // Método para validar contraseña
    public boolean validarPassword(String password) {
        return password != null && password.length() >= 8;
    }

    // Método para encriptar la contraseña
    public String encriptarPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Método para verificar la contraseña encriptada
     public boolean verificarPassword(String password, String passwordEncriptada) {
        return passwordEncoder.matches(password, passwordEncriptada);
    }

}
