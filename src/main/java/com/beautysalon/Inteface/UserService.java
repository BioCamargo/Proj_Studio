package com.beautysalon.Inteface;

import com.beautysalon.DTO.UserDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserDTO> listarTodos();
    UserDTO buscarPorId(Long id);
    UserDTO salvar(UserDTO dto) throws IOException;

    void salvarData(UserDTO userDTO);

    UserDTO atualizar(Long id, UserDTO dto);
    void deletar(Long id);

    boolean userExists(String username);


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void registerNewUser(UserDTO userDTO);
}

