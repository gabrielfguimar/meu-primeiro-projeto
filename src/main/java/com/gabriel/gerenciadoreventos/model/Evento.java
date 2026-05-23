package com.gabriel.gerenciadoreventos.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do evento é obrigatório.")
    @Size(min = 3, message = "O nome do evento deve ter pelo menos 3 caracteres.")
    private String nome;

    @NotBlank(message = "O local do evento é obrigatório.")
    private String local;

    @NotNull(message = "A data do evento é obrigatória.")
    @Column(name = "data", columnDefinition = "DATE")
    private LocalDate data;

    // Construtor Padrão (Obrigatório para o Hibernate)
    public Evento() {}

    // Construtores, Getters e Setters para podermos acessar os dados
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}