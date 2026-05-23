package com.gabriel.gerenciadoreventos.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel.gerenciadoreventos.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Captura a chave secreta definida no application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // 1. Gera o Token JWT quando o usuário faz login com sucesso
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("gerenciador-eventos-api") // Identificador da sua API
                    .withSubject(usuario.getLogin())       // Guarda o login do usuário dentro do token
                    .withExpiresAt(gerarDataExpiracao())   // Tempo de validade do token
                    .sign(algorithm);                      // Assina digitalmente
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // 2. Valida o Token recebido nas requisições e extrai o login do usuário
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("gerenciador-eventos-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o login se o token for válido
        } catch (JWTVerificationException exception) {
            // Se o token estiver expirado ou adulterado, retorna vazio (não autentica)
            return "";
        }
    }

    // Define que o token vai expirar em 2 horas (Horário de Brasília)
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}