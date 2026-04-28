package com.beautysalon.Implementacao;

import com.beautysalon.DTO.ClienteDTO;
import com.beautysalon.Inteface.ClienteService;
import com.beautysalon.converter.ClienteConverter;
import com.beautysalon.model.Cliente;
import com.beautysalon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteConverter clienteConverter;



    @Override
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {
        try {
            return clienteRepository.findById(id)
                    .map(clienteConverter::convert)
                    .orElseThrow(() -> new RuntimeException("Client Not found"));
        } catch (Exception e) {
            logger.error("Error searching for client by ID " + id, e);
            throw e;
        }}

    @Override
    public ClienteDTO salvar(ClienteDTO dto) {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome(dto.getNome());
            cliente.setEmail(dto.getEmail());
            Cliente salvo = clienteRepository.save(cliente);
            return clienteConverter.convert(salvo);
        }catch (Exception e) {
            logger.error("Error saving client", e);
            throw e;
        }
    }

    @Override
    public void deletar(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar cliente com ID " + id, e);
            throw e;
        }
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
    @Override
    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            cliente.setNome(dto.getNome());
            cliente.setEmail(dto.getEmail());
            Cliente atualizado = clienteRepository.save(cliente);
            return toDTO(atualizado);
        } catch (Exception e) {
            logger.error("Erro ao atualizar cliente com ID " + id, e);
            throw e;
        }
    }

    @Override
    public List<ClienteDTO> buscarPorNomeParcial(String nome)
    {
        return clienteRepository.buscarPorNomeParcial(nome).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
