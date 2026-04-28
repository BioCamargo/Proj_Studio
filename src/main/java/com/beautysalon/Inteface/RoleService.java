package com.beautysalon.Inteface;

import com.beautysalon.DTO.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> listarTodos();
    RoleDTO buscarPorId(Long id);
    RoleDTO salvar(RoleDTO dto);
    RoleDTO atualizar(Long id, RoleDTO dto);
    void deletar(Long id);
}
