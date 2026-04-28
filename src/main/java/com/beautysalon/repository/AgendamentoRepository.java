package com.beautysalon.repository;

import com.beautysalon.model.Agendamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    @Override
    @EntityGraph(attributePaths = {"cliente", "servicos"})
    List<Agendamento> findAll();

    @Query("SELECT a FROM Agendamento a JOIN a.servicos s WHERE s.id = :servicoId")
    List<Agendamento> findByServicoId(Long servicoId);

}
