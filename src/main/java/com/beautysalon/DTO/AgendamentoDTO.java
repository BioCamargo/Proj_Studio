package com.beautysalon.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AgendamentoDTO
{
    private Long id;

    @NotNull(message = "The date is not mandatory")
    @FutureOrPresent(message = "Date and time must be in present or future")
    private LocalDateTime dataHora;

    @NotNull(message = "The client ID is required")
    private Long clienteId;

    @NotNull(message = "The client ID is required")
    private Long servicoId;
//-------------------------------------------------
    private String clienteNome;
    private String servicoNome;
    private String status; // Add this field

    // Add getter and setter
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


    // Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public Long getClienteId() {return clienteId;}
    public void setClienteId(Long clienteId) {this.clienteId = clienteId;}

    public Long getServicoId() {return servicoId;}
    public void setServicoId(Long servicoId) {this.servicoId = servicoId;}

    public String getServicoNome() { return servicoNome; }
    public void setServicoNome(String servicoNome) { this.servicoNome = servicoNome; }
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
}
