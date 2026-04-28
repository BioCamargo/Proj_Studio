package com.beautysalon.Controller;

import com.beautysalon.DTO.UserDTO;
import com.beautysalon.Inteface.RoleService;
import com.beautysalon.Inteface.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/usuarios")
public class UserController {

//    @GetMapping
//    public String redirect() {
//        return "redirect:/usuarios";
//    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;




    @GetMapping
    public String listar(Model model, Principal principal) {
        try {
            System.out.println("Acess to user list" + principal.getName());
            model.addAttribute("usuarios", userService.listarTodos());
            model.addAttribute("pageTitle", "Lista de Usuários");
            return "usuario/list";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar usuários: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", userService.buscarPorId(id));
        model.addAttribute("roles", roleService.listarTodos());
        model.addAttribute("pageTitle", "Editar Usuário");
        return "usuario/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuario") UserDTO dto, BindingResult result, Model model) throws IOException {


        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.listarTodos());
            return "usuario/form";
        }
        userService.salvar(dto);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("usuario") UserDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.listarTodos());
            return "usuario/form";
        }
        userService.atualizar(id, dto);
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        userService.deletar(id);
        return "redirect:/usuarios";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO,
                               BindingResult result,
                               Model model) throws IOException {

        // Verifica se username já existe
        if(userService.userExists(userDTO.getUsername())) {
            result.rejectValue("username", "error.userDTO", "Nome de usuário já existe");
        }

        if (result.hasErrors()) {
            model.addAttribute("contentPage", "auth/registerContent");
            return "home/index";
        }

        userService.registerNewUser(userDTO);
        return "redirect:/login?registered";
    }
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("usuario", new UserDTO());
        model.addAttribute("roles", roleService.listarTodos());
        model.addAttribute("pageTitle", "Novo Usuário");
        return "usuario/form";
    }


//    @GetMapping("/{id}")
//    public String detalhes(@PathVariable Long id, Model model) {
//        model.addAttribute("user", userService.buscarPorId(id));
//        return "user/detail";
//    }
}
