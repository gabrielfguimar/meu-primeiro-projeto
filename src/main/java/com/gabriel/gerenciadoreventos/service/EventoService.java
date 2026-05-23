package com.gabriel.gerenciadoreventos.service;

import com.gabriel.gerenciadoreventos.dto.EventoRequestDTO;
import com.gabriel.gerenciadoreventos.dto.EventoResponseDTO;
import com.gabriel.gerenciadoreventos.model.Evento;
import com.gabriel.gerenciadoreventos.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    // Injeção de dependência do repositório para falar com o banco
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // 1. Listar todos os eventos
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAll()
                .stream()
                .map(EventoResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 2. Criar um novo evento
    public EventoResponseDTO criarEvento(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setNome(dto.nome());
        evento.setLocal(dto.local());
        evento.setData(dto.data()); // Vincula o LocalDate perfeitamente

        Evento eventoSalvo = eventoRepository.save(evento);
        return new EventoResponseDTO(eventoSalvo);
    }

    // 3. Atualizar um evento existente
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {
        return eventoRepository.findById(id)
                .map(eventoExistente -> {
                    eventoExistente.setNome(dto.nome());
                    eventoExistente.setLocal(dto.local());
                    eventoExistente.setData(dto.data());
                    Evento atualizado = eventoRepository.save(eventoExistente);
                    return new EventoResponseDTO(atualizado);
                })
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o ID: " + id));
    }

    // 4. Deletar um evento
    public void deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado com o ID: " + id);
        }
        eventoRepository.deleteById(id);
    }
}