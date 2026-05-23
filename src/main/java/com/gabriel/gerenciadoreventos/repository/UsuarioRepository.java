package com.gabriel.gerenciadoreventos.repository;

import com.gabriel.gerenciadoreventos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring Security usa o retorno do tipo UserDetails para fazer a checagem interna
    UserDetails findByLogin(String login);
}