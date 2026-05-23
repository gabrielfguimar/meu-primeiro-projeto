package com.gabriel.gerenciadoreventos.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EventoRequestDTO(
    @NotBlank(message = "O nome do evento é obrigatório")
    String nome,

    @NotBlank(message = "O local do evento é obrigatório")
    String local,

    @NotNull(message = "A data do evento é obrigatória")
    @FutureOrPresent(message = "A data do evento não pode ser retroativa")
    LocalDate data
) {}