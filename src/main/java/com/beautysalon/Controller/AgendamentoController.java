package com.beautysalon.Controller;


import com.beautysalon.DTO.AgendamentoDTO;
import com.beautysalon.Implementacao.ClienteServiceImpl;
import com.beautysalon.Inteface.AgendamentoService;
import com.beautysalon.Inteface.ClienteService;
import com.beautysalon.Inteface.ServicoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicoService servicoService;


    @GetMapping
    public String listar(Model model)
    {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarTodos();
        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("servicos", servicoService.listarTodos()); // Add this
        return "agendamentos/list";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        model.addAttribute("agendamento", agendamentoService.buscarPorId(id));
        return "agendamentos/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute ("agendamento") AgendamentoDTO agendamentoDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("servicos", servicoService.listarTodos());
            return "agendamentos/form";

        }
        try {
            agendamentoService.salvar(agendamentoDTO);
            return "redirect:/agendamentos/" + agendamentoDTO.getId();
        } catch (RuntimeException e) {

            // Log do erro
            Logger logger = LoggerFactory.getLogger(AgendamentoController.class);
            logger.error("Erro ao salvar agendamento: ", e);

            model.addAttribute("error", e.getMessage());
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("servicos", servicoService.listarTodos());

            return "error/customError";
//            return "agendamentos/form";
        }
    }
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("agendamento", new AgendamentoDTO());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicos", servicoService.listarTodos());
        return "agendamentos/form";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("agendamento") AgendamentoDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("servicos", servicoService.listarTodos());
            return "agendamentos/form";
        }
        agendamentoService.atualizar(id, dto);
        return "redirect:/agendamentos";
    }

    @PostMapping("/deletar/{id}")
    @ResponseBody
    public ResponseEntity<?>  deletar(@PathVariable Long id)
    {
        try {

            agendamentoService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/servico/{id}")
    public String listarPorServico(@PathVariable("id") Long servicoId, Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarPorServico(servicoId));
        return "agendamentos/list";
    }


}
