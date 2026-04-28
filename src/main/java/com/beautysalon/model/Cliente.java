package com.beautysalon.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "clientes") //same
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamentos;

    // Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return this.nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return this.telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;
    }

    public List<Agendamento> getAgendamentos() {return agendamentos;}
    public void setAgendamentos(List<Agendamento> agendamentos) {this.agendamentos = agendamentos;}

    public LocalDate getDataNascimento() {

        return null;
    }
}