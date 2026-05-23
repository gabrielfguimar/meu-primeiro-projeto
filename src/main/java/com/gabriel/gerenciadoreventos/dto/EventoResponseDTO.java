package com.gabriel.gerenciadoreventos.dto;

import com.gabriel.gerenciadoreventos.model.Evento;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EventoResponseDTO(
    Long id,
    String nome,
    String local,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate data
) {
    public EventoResponseDTO(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getLocal(), evento.getData());
    }
}