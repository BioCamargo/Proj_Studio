package com.beautysalon.Implementacao;

import com.beautysalon.DTO.RoleDTO;
import com.beautysalon.DTO.UserDTO;
import com.beautysalon.Inteface.UserService;
import com.beautysalon.model.Role;
import com.beautysalon.model.User;
import com.beautysalon.repository.RoleRepository;
import com.beautysalon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> listarTodos() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());

    }

    @Override
    public UserDTO buscarPorId(Long id) {
        return userRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    }

    @Override
    public UserDTO salvar(UserDTO dto) throws IOException {
        User user = toEntity(dto);
        if (dto.getImagem() != null && !dto.getImagem().isEmpty()) {
            // Create uploads directory in the project root
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomeArquivo = UUID.randomUUID() + "_" + dto.getImagem().getOriginalFilename();
            Path caminho = uploadPath.resolve(nomeArquivo);
            dto.getImagem().transferTo(caminho.toFile());
            user.setImagem("/uploads/" + nomeArquivo);
        }
        return toDTO(userRepository.save(user));
    }
    @Override
    public void salvarData(UserDTO userDTO) {
//        User user = new User();
//        user.setNome(userDTO.getUsername());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setEmail(userDTO.getEmail());
//        user.setEnabled(true);
//
//        // Adicione roles padrão se necessário
//        // Adicione roles padrão (ROLE_USER)
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleRepository.findByNome("ROLE_USER"));
//        user.setRoles(roles);
//
//        userRepository.save(user);
    }

    @Override
    public UserDTO atualizar(Long id, UserDTO dto) {
        User existente = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existente.setNome(dto.getUsername());
        existente.setEmail(dto.getEmail());
        if (dto.getPassword() != null) {
            existente.setPassword(dto.getPassword()); // Lembre de codificar!
        }
        existente.setRoles(buscarRoles(dto.getRoleIds()));
        return toDTO(userRepository.save(existente));

    }

    @Override
    public void deletar(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean userExists(String username)
    {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void registerNewUser(UserDTO userDTO) {
        System.out.println("Senha original: " + userDTO.getPassword());
        String encoded = passwordEncoder.encode(userDTO.getPassword());
        System.out.println("Senha codificada: " + encoded);

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoded); // já está codificada
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);

        // Sempre atribui ROLE_USER como padrão
        Role defaultRole = roleRepository.findByNome("ROLE_USER");
        if (defaultRole == null) {
            // Se não existir no banco, cria na hora
            defaultRole = new Role();
            defaultRole.setNome("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        userRepository.save(user);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoleIds(user.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        dto.setImagemUrl(user.getImagem());

        dto.setRoles(user.getRoles().stream().map(role -> {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setNome(role.getNome());
            return roleDTO;
        }).collect(Collectors.toSet()));

        if(user.getRoles() != null) {
            dto.setRoleIds(user.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername()); //para poder passar no login do user
        user.setNome(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // Lembre de codificar!
        user.setRoles(buscarRoles(dto.getRoleIds()));
        return user;
    }

    private Set<Role> buscarRoles(Set<Long> ids) {
        return ids.stream().map(id -> roleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Role não encontrada: " + id)))
                .collect(Collectors.toSet());
    }
}
