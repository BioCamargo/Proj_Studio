package com.beautysalon.Controller;

import com.beautysalon.DTO.UserDTO;
import com.beautysalon.Inteface.RoleService;
import com.beautysalon.Inteface.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("contentPage", "auth/login");
        model.addAttribute("pageTitle", "Login- Beauty Salon");
        return "home/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        System.out.println("ENTREI");
        model.addAttribute("contentPage", "auth/register");
        model.addAttribute("pageTitle", "Registro - Beauty Salon");
        model.addAttribute("userDTO", new UserDTO());
        return "home/index";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registerUser(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        // validações
        if (userService.existsByUsername(userDTO.getUsername())) {
            result.rejectValue("username", "error.userDTO", "Este nome de usuário já está em uso");
        }

        if (userService.existsByEmail(userDTO.getEmail())) {
            result.rejectValue("email", "error.userDTO", "Este email já está cadastrado");
        }

        if (result.hasErrors()) {
            response.put("success", false);
            response.put("errors", result.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .toList());
            return response;
        }

        // salva usuário
        userService.registerNewUser(userDTO);

        response.put("success", true);
        return response;
    }


//    @PostMapping("/register")
//    public String registerUser(
//        @Valid @ModelAttribute("userDTO") UserDTO userDTO,
//                               BindingResult result,
//                               Model model) throws IOException {
//        // Verifica se username já existe
//        if(userService.existsByUsername(userDTO.getUsername())) {
//            result.rejectValue("username", "error.userDTO", "Este nome de usuário já está em uso");
//        }
//
//        // Verifica se email já existe
//        if(userService.existsByEmail(userDTO.getEmail())) {
//            result.rejectValue("email", "error.userDTO", "Este email já está cadastrado");
//        }
//
//        if(result.hasErrors()) {
//            model.addAttribute("contentPage", "auth/register");
//            model.addAttribute("pageTitle", "Registro - Beauty Salon");
//            return "home/index";
//        }
//
//        userService.registerNewUser(userDTO);
//        return "redirect:/login?registered";
//    }
//









//    @GetMapping("/register")
//    public String formRegistro(Model model) {
//        model.addAttribute("user", new UserDTO());
//        return "auth/login";
//    }
//
//    @PostMapping("/register")
//    public String registrar(@ModelAttribute UserDTO userDTO) throws IOException {
//        userService.salvar(userDTO); // Deve encriptar a senha dentro do service
//        return "redirect:/login";
    }



