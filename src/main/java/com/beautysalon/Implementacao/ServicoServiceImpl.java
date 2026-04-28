package com.beautysalon.Implementacao;

import com.beautysalon.DTO.ServicoDTO;
import com.beautysalon.Inteface.ServicoService;
import com.beautysalon.model.Servico;
import com.beautysalon.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ServicoServiceImpl implements ServicoService {


    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    public ServicoDTO salvar(ServicoDTO dto)
    {
        Servico servico = new Servico();


        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setPreco(dto.getPreco());
        servico.setImagem(dto.getImagem());

        Servico salvo = servicoRepository.save(servico);

        dto.setId(salvo.getId());
        return dto;
    }

    @Override
    public List<ServicoDTO> listarTodos()
    {
        return servicoRepository.findAll().stream().map(s -> {
            ServicoDTO dto = new ServicoDTO();
            dto.setId(s.getId());
            dto.setNome(s.getNome());
            dto.setDescricao(s.getDescricao());
            dto.setPreco(s.getPreco());
            dto.setImagem(s.getImagem());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ServicoDTO buscarPorId(Long id)
    {
        return servicoRepository.findById(id).map(s -> {
            ServicoDTO dto = new ServicoDTO();
            dto.setId(s.getId());
            dto.setNome(s.getNome());
            dto.setDescricao(s.getDescricao());
            dto.setPreco(s.getPreco());
            return dto;
        }).orElse(null);

    }

    @Override
    public void excluir(Long id)
    {
        servicoRepository.deleteById(id);

    }
    @Override
    public ServicoDTO atualizar(Long id, ServicoDTO dto) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setPreco(dto.getPreco());

        Servico atualizado = servicoRepository.save(servico);

        ServicoDTO atualizadoDTO = new ServicoDTO();
        atualizadoDTO.setId(atualizado.getId());
        atualizadoDTO.setNome(atualizado.getNome());
        atualizadoDTO.setDescricao(atualizado.getDescricao());
        atualizadoDTO.setPreco(atualizado.getPreco());

        return atualizadoDTO;
    }

}
