package FCA.Sistema.Web.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import FCA.Sistema.Web.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").authenticated()
                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                .requestMatchers("/historialSemestres/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                .requestMatchers("/documentos/**").hasAnyAuthority("ADMIN", "SUPERADMIN", "USER")
                .requestMatchers("/estudiantes/**").hasAnyAuthority("ADMIN", "SUPERADMIN", "USER")
                .requestMatchers("/pagos/**").hasAnyAuthority("ADMIN", "SUPERADMIN", "USER")
                .requestMatchers("/tipopago/**").hasAuthority("SUPERADMIN")
                .requestMatchers("/tipodocumento/**").hasAuthority("SUPERADMIN")
                .requestMatchers("/unidades/**").hasAuthority("SUPERADMIN")
                .requestMatchers("/usuarios/**").hasAnyAuthority("ADMIN", "SUPERADMIN")
                .requestMatchers("/tipoprograma/**").hasAnyAuthority("SUPERADMIN")  
                .requestMatchers("/programas/**").hasAnyAuthority("ADMIN", "SUPERADMIN") 
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }



}

