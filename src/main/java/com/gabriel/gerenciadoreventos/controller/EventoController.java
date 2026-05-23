package com.gabriel.gerenciadoreventos.controller;

import com.gabriel.gerenciadoreventos.dto.EventoRequestDTO;
import com.gabriel.gerenciadoreventos.dto.EventoResponseDTO;
import com.gabriel.gerenciadoreventos.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listarEventos() {
        List<EventoResponseDTO> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos); // Se no service for listarTodos
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criarEvento(@Valid @RequestBody EventoRequestDTO dto) {
        EventoResponseDTO novoEvento = eventoService.criarEvento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizarEvento(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO dto) {
        // Se o seu service retornar um Optional ou o objeto direto, ajustamos aqui:
        try {
            EventoResponseDTO atualizado = eventoService.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}