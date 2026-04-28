package com.beautysalon.Implementacao;

import com.beautysalon.DTO.AgendamentoDTO;
import com.beautysalon.DTO.ServicoDTO;
import com.beautysalon.Inteface.AgendamentoService;
import com.beautysalon.converter.AgendamentoConverter;
import com.beautysalon.model.Agendamento;
import com.beautysalon.model.Cliente;
import com.beautysalon.model.Servico;
import com.beautysalon.repository.AgendamentoRepository;
import com.beautysalon.repository.ClienteRepository;
import com.beautysalon.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service // Isso informa ao Spring que esta classe é um componente de
// serviço e deve ser gerenciada pelo contêiner de injeção de dependência.

public class AgendamentoServiceImpl  implements AgendamentoService
{
     @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    //converter
    @Autowired
    private AgendamentoConverter agendamentoConverter;


    @Override
    public List<AgendamentoDTO> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(agendamentoConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AgendamentoDTO buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamentoConverter::toDTO)
                .orElseThrow(() -> new RuntimeException("Agenda not found"));

    }

    @Override
    public AgendamentoDTO salvar(AgendamentoDTO dto) {
        Agendamento agendamento = agendamentoConverter.toEntity(dto);
        agendamento.setDataHora(dto.getDataHora());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        agendamento.setCliente(cliente);

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        agendamento.getServicos().add(servico);
        if (agendamento.getServicos()==null)
        {
            agendamento.setServicos(new ArrayList<>());
        }

        Agendamento saved = agendamentoRepository.save(agendamento);
        agendamentoRepository.flush();
        return agendamentoConverter.toDTO(saved);
    }

    @Override
    public AgendamentoDTO atualizar(Long id, AgendamentoDTO dto) {
    Agendamento existente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agenda not found"));



        existente.setDataHora(dto.getDataHora());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        existente.setCliente(cliente);

        existente.getServicos().clear();
        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        existente.getServicos().add(servico);

        //converter
        Agendamento atualizado= agendamentoConverter.toEntity(dto);
        atualizado.setId(existente.getId());

        return agendamentoConverter.toDTO(agendamentoRepository.save(atualizado));
    }

    @Override
    public void deletar(Long id) {
        agendamentoRepository.deleteById(id);

    }



    private AgendamentoDTO toDTO(Agendamento a) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(a.getId());
        dto.setDataHora(a.getDataHora());
        dto.setClienteId(a.getCliente().getId());
        dto.setClienteNome(a.getCliente().getNome());


        if (!a.getServicos().isEmpty()) {

            dto.setServicoId(a.getServicos().get(0).getId());
            dto.setServicoNome(a.getServicos().get(0).getNome());
//            dto.setServicoNome(dto.getServicoNome());
//            dto.setStatus(a.getStatus());
        }
        if (a.getCliente() != null) {
            dto.setClienteId(a.getCliente().getId());
            dto.setClienteNome(a.getCliente().getNome());
        }

        return dto;
    }

    private Agendamento toEntity(AgendamentoDTO dto) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.getDataHora());

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        agendamento.setCliente(cliente);

        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        agendamento.getServicos().add(servico);

        return agendamento;
    }

    //query servicolstar
    public List<Agendamento> listarPorServico(Long servicoId) {
        return agendamentoRepository.findByServicoId(servicoId);
    }
//    @Override
//    public List<AgendamentoDTO> listarPorServico(ServicoDTO servico) {
//        return List.of();
//    }

}
