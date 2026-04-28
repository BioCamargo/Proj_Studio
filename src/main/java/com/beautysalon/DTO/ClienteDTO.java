package com.beautysalon.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ClienteDTO
{
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters.")
    private String nome;

    @NotBlank(message = "email is required")
    @Email(message = "Email must be valid")
    private String email;

    private String telefone; //vamos ver
    private LocalDate dataNascimento; //vamos ver

    public ClienteDTO (Long id, String nome, String email)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public ClienteDTO() {

    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public LocalDate getDataNascimento() {return dataNascimento;}
    public void setDataNascimento(LocalDate dataNascimento) {this.dataNascimento = dataNascimento;}

}
