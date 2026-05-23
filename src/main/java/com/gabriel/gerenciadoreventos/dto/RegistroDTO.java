package com.gabriel.gerenciadoreventos.dto;

import com.gabriel.gerenciadoreventos.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDTO(
    @NotBlank(message = "O login é obrigatório.") String login,
    @NotBlank(message = "A senha é obrigatória.") String senha,
    @NotNull(message = "O cargo/role é obrigatório.") UserRole role
) {}