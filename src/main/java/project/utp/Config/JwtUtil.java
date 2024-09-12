package project.utp.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/*
@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extracAllClaim(token);
        return claimsResolver.apply(claims);
    }

    //obtener todos los claims del token
    public Claims extracAllClaim(String token){
        return   Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //btener firma del token
    public Key getSignatureKey(){

        try {
            byte[] keyByte = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyByte);
        } catch (Exception e) {
            log.error("Error decodificando secret key", e);
            throw new RuntimeException(e);
        }
    }

   // private Claims extractAllClaims(String token) {
     //   return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    //}

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}*/

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.utp.modelo.Usuario;
import project.utp.modelo.UsuarioRol;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    public String generateToken(String email) {


        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    //obtener todos los claims del token
    public Claims extracAllClaim(String token) {
        return Jwts.parser()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //btener firma del token
    public Key getSignatureKey() {

        try {
            byte[] keyByte = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyByte);
        } catch (Exception e) {
            log.error("Error decodificando secret key", e);
            throw new RuntimeException(e);
        }
    }

    //obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
        Claims claims = extracAllClaim(token);
        return claimsFunction.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getemailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //validar tokes
    public boolean isTokeValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                log.error("Token inválido: no contiene exactamente 2 caracteres de punto");
                return false;
            }

            Jwts.parser()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;

        } catch (Exception e) {
            log.error("token invalido,error: ".concat(e.getMessage()));
            return false;
        }
    }

    //obtener el email del token
    public String getemailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
}










    /*@Autowired
    private JwtConfig jwtConfig;

    public String tokenGenerate(Usuario usuario){
        Claims claims = Jwts.claims().setSubject(usuario.getId_usuario().toString());
        claims.put("email", usuario.getEmail());
        for (UsuarioRol usuarioRol : usuario.getUsuarioRoles()) {
            claims.put("rol_id", usuarioRol.getRol().getId_rol());
            break;
        }
        claims.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }*/



