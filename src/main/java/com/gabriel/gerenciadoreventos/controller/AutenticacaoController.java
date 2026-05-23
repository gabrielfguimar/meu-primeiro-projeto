package com.gabriel.gerenciadoreventos.controller;

import com.gabriel.gerenciadoreventos.dto.AutenticacaoDTO;
import com.gabriel.gerenciadoreventos.dto.RegistroDTO;
import com.gabriel.gerenciadoreventos.model.Usuario;
import com.gabriel.gerenciadoreventos.repository.UsuarioRepository;
import com.gabriel.gerenciadoreventos.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, 
                                  UsuarioRepository usuarioRepository, 
                                  TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    // Endpoint de Login: Valida as credenciais e devolve o Token JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AutenticacaoDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Se passar pela autenticação, gera o token digital
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        // Retorna o token em um objeto JSON simples
        return ResponseEntity.ok().body(new TokenResponse(token));
    }

    // Endpoint de Registro: Criptografa a senha e salva o usuário no banco
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody @Valid RegistroDTO data) {
        if (this.usuarioRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Erro: Este login já está cadastrado no sistema.");
        }

        // Criptografa a senha usando BCrypt antes de persistir
        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.login(), senhaCriptografada, data.role());

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().body("Usuário registrado com sucesso!");
    }

    // Classe auxiliar local para estruturar a resposta do token em JSON
    private record TokenResponse(String token) {}
}