package com.beautysalon.Controller;

import com.beautysalon.DTO.RoleDTO;
import com.beautysalon.Inteface.RoleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String listar(Model model) {
        logger.info("Acessando listagem de roles");
        model.addAttribute("roles", roleService.listarTodos());
        return "roles/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("role", new RoleDTO());
        model.addAttribute("pageTitle", "Nova Role");
        return "roles/form";
    }

    // Adicione este endpoint para o AJAX
    @GetMapping("/api")
    @ResponseBody
    public List<RoleDTO> listarApi() {
        return roleService.listarTodos();
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.buscarPorId(id));
        return "roles/list";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("role") RoleDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "roles/form";

        }
        roleService.salvar(dto);
        return "redirect:/roles";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("role") RoleDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return "roles/form";
        }
        roleService.atualizar(id, dto);
        return "redirect:/roles";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        roleService.deletar(id);
        return "redirect:/roles";
    }
}
