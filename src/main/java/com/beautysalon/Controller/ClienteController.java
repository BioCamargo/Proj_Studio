package com.beautysalon.Controller;

import com.beautysalon.DTO.ClienteDTO;
import com.beautysalon.Inteface.ClienteService;
import com.beautysalon.model.Cliente;
import com.beautysalon.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private SmsService smsService;



    @GetMapping
    public String listar(Model model) {
        List<ClienteDTO> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        return "clientes/list"; // Criar JSP correspondente depois
    }

    @GetMapping("/novo")
    public String novoCliente(Model model) {
        model.addAttribute("cliente", new ClienteDTO());
        model.addAttribute("pageTitle", "Novo Cliente");
        return "clientes/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("clientes") ClienteDTO clienteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", clienteDTO);
            return "clientes/form";
        }
        clienteService.salvar(clienteDTO);
        return "redirect:/clientes"; //no ex n tem listar
    }

    @GetMapping("/delete/{id}")
    public String deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return "redirect:/clientes";
    }

    @GetMapping("/form")
    public String exibirFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form"; // se o JSP estiver em views/clientes/form.jsp
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, @Valid @ModelAttribute("cliente") ClienteDTO clienteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", clienteDTO);
            return "clientes/form";
        }
        clienteService.salvar(clienteDTO);
        return "redirect:/clientes/list"; //no ex n tem listar


    }
    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam("nome") String nome, Model model) {
        List<ClienteDTO> resultados = clienteService.buscarPorNomeParcial(nome);
        model.addAttribute("clientes", resultados);
        return "clientes/list";
    }


    @PostMapping("/clientes/{id}/enviar-sms-confirmacao")
    public ResponseEntity<String> enviarSmsConfirmacao(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.buscarPorId(id);
        try {
            String resposta = smsService.enviarSms(
                    cliente.getTelefone(),
                    "Your registration has been confirmed! Code: " + gerarCodigo()
            );
            return ResponseEntity.ok("SMS sent: " + resposta);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error to send SMS");
        }
    }

    private String gerarCodigo() {
        return String.format("%06d", new Random().nextInt(999999));
    }



}
