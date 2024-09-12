package project.utp.Config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import project.utp.service.detalleUsuarioService;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Securityconfig  {

    @Autowired
    private JwtAuthenticatioEntryPoint authenticatioEntryPoint;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private detalleUsuarioService detalleusuarioservice;




    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,  AuthenticationManager authenticationManager) throws Exception {

     /* JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil);
       jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
       jwtAuthenticationFilter.setFilterProcessesUrl("/login");*/

       return http
               .csrf(config -> config.disable())
               .authorizeHttpRequests( auth -> {
                   auth.requestMatchers("/auth/generartoken","/producto/listar","categoria/listar").permitAll();
                   auth.anyRequest().authenticated();
               })

               .exceptionHandling().authenticationEntryPoint(authenticatioEntryPoint)
               .and()
               .sessionManagement(session -> {
                   session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
               })
               .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
              // .addFilter(jwtAuthenticationFilter)
               .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }



}
