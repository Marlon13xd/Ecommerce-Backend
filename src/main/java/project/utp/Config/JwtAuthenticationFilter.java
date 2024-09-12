package project.utp.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.utp.modelo.Usuario;
import project.utp.service.detalleUsuarioService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private detalleUsuarioService detalleusuarioservice;

   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       String requestTokenHeader = request.getHeader("Authorization");
       String email = null;
       String jwtToken = null;

       System.out.println("Authorization Header: " + requestTokenHeader);
       System.out.println("JWT Token: " + jwtToken);

       if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
           requestTokenHeader = requestTokenHeader.trim();
           jwtToken = requestTokenHeader.substring(6);
           try {

               email = jwtUtil.extractUsername(jwtToken);

           } catch (ExpiredJwtException expiredJwtException) {

               System.out.println("El token  ha expirado");

           } catch (Exception e) {
               e.printStackTrace();
           }
       } else {
           System.out.println("Token invalido, no empieza con bearer");
       }

       if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = detalleusuarioservice.loadUserByUsername(email);
           if (jwtUtil.validateToken(jwtToken, userDetails)) {
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
       }
       else {
           System.out.println("el token no es valido");
       }
       filterChain.doFilter(request,response);
   }
   }
/*
@AllArgsConstructor
 public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Usuario usuario = null;
        String email;
        String password;

        try {
            usuario= new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            email = usuario.getEmail();
            password = usuario.getPassword();

        } catch (IOException e) {
                throw new RuntimeException(e);
            }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken( email,password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String token= jwtUtil.generateToken(user.getUsername()) ;

        response.addHeader("Authorization",token);

        Map<String,Object> httpReponse = new HashMap<>();
        httpReponse.put("token",token);
        httpReponse.put("Message" , "Autenticacion correcta");
        httpReponse.put("Email", user.getUsername());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpReponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();


        super.successfulAuthentication(request, response, chain, authResult);
    }*/

