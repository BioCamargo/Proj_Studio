package com.beautysalon.Implementacao;

import com.beautysalon.model.Role;
import com.beautysalon.model.User;
import com.beautysalon.repository.RoleRepository;
import com.beautysalon.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            criarRolesIniciais();
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            criarUsuarioAdmin();
        }
    }

    private void criarRolesIniciais() {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        roleRepository.saveAll(List.of(adminRole, userRole));
    }

    private void criarUsuarioAdmin() {
        // Tenta encontrar a role ADMIN
        Optional<Role> adminRoleOpt = Optional.ofNullable(roleRepository.findByNome("ADMIN"));

        // Se não existir, cria a role
        Role adminRole = adminRoleOpt.orElseGet(() -> {
            Role newRole = new Role("ADMIN");
            return roleRepository.save(newRole);
        });

        // Cria o usuário admin se não existir
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setNome("Administrator");
            admin.setUsername("admin");
            admin.setEmail("admin@beautysalon.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAtivo(true);
            admin.setRoles(Set.of(adminRole));

            userRepository.save(admin);
        }
    }
}
