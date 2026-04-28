package com.beautysalon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "servicos") //add de ultima hora, verificar se e necessario
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private String imagem;


    @ManyToMany(mappedBy = "servicos")
    @JsonIgnore
    private List<Agendamento> agendamentos;

    // Getters e Setters
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public BigDecimal getPreco() {return preco;}
    public void setPreco(BigDecimal preco) {this.preco = preco;}

    public String getImagem() {return imagem;}
    public void setImagem(String imagem) {this.imagem = imagem;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public List<Agendamento> getAgendamentos() {return agendamentos;}

    public void setAgendamentos(List<Agendamento> agendamentos) {this.agendamentos = agendamentos;}
}
