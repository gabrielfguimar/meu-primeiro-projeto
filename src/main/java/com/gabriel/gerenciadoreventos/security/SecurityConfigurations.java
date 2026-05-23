package com.gabriel.gerenciadoreventos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Permite que qualquer um tente fazer Login ou se Cadastrar
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/registrar").permitAll()
                        // Permite que qualquer um veja os eventos (GET público)
                        .requestMatchers(HttpMethod.GET, "/api/eventos").permitAll()
                        // Bloqueia qualquer alteração de eventos (POST, PUT, DELETE) apenas para ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/eventos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // Adiciona o nosso filtro de validação JWT antes do filtro de autenticação padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define que usaremos o BCrypt para criptografar as senhas antes de salvar no banco
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}