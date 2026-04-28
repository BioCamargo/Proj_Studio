package com.beautysalon.Implementacao;

import com.beautysalon.DTO.RoleDTO;
import com.beautysalon.Inteface.RoleService;
import com.beautysalon.model.Role;
import com.beautysalon.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl  implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDTO> listarTodos() {
        return roleRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());

    }

    @Override
    public RoleDTO buscarPorId(Long id) {
        return roleRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

    }

    @Override
    public RoleDTO salvar(RoleDTO dto) {
        Role role = new Role();
        role.setNome(dto.getNome());
        return toDTO(roleRepository.save(role));

    }

    @Override
    public RoleDTO atualizar(Long id, RoleDTO dto) {
        Role existente = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));
        existente.setNome(dto.getNome());
        return toDTO(roleRepository.save(existente));

    }

    @Override
    public void deletar(Long id) {
        roleRepository.deleteById(id);

    }
    private RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setNome(role.getNome());
        return dto;
    }
}
