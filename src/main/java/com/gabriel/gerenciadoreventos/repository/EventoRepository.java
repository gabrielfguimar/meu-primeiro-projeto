package com.gabriel.gerenciadoreventos.repository;

import com.gabriel.gerenciadoreventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Pronto! Herdando de JpaRepository, já temos o CRUD completo nas mãos.
}