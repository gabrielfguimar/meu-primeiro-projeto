package com.gabriel.gerenciadoreventos.dto;

import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
    @NotBlank(message = "O login é obrigatório.") String login,
    @NotBlank(message = "A senha é obrigatória.") String senha
) {}