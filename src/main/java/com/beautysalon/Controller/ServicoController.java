package com.beautysalon.Controller;
import com.beautysalon.DTO.ServicoDTO;
import com.beautysalon.Inteface.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    private final String uploadDir = "C:/beautysalon/uploads/";


    @GetMapping
    public String listar(Model model) {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        model.addAttribute("servicos", servicos);
        model.addAttribute("pageTitle", "Lista de Serviços");
        return "servicos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("servico", new ServicoDTO());
        model.addAttribute("pageTitle", "Novo Serviço");
        model.addAttribute("formAction", "/servicos"); // Define a ação do formulário
        return "servicos/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute ("servico")ServicoDTO dto,
                          BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", dto.getId() == null ? "Novo Serviço" : "Editar Serviço");
            return "servicos/form";
        }

        if (dto.getImagemFile() != null && !dto.getImagemFile().isEmpty()) {
            Files.createDirectories(Paths.get(uploadDir)); // Cria a pasta se não existir

            String nomeArquivo = System.currentTimeMillis() + "_" + dto.getImagemFile().getOriginalFilename();
            Path caminhoCompleto = Paths.get(uploadDir + nomeArquivo);
            Files.createDirectories(caminhoCompleto.getParent());
            dto.getImagemFile().transferTo(caminhoCompleto.toFile());
            dto.setImagem( nomeArquivo);
        }

        servicoService.salvar(dto);
        return "redirect:/servicos";

    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ServicoDTO servico = servicoService.buscarPorId(id);
        model.addAttribute("servico", servico);
        model.addAttribute("pageTitle", "Editar Serviço");
        model.addAttribute("formAction", "/servicos/" + id + "/atualizar"); // Ação para edição
        return "servicos/form";
    }
//@RequestParam("imagem") MultipartFile imagemFile
    @PostMapping("/{id}/atualizar")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute ServicoDTO dto, BindingResult result,
                            Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Editar Serviço");
            return "servicos/form";
        }

        if (dto.getImagemFile() != null && !dto.getImagemFile().isEmpty()) {
            Files.createDirectories(Paths.get(uploadDir)); // garante que existe

            String nomeArquivo = System.currentTimeMillis() + "_" + dto.getImagemFile().getOriginalFilename();
            Path caminhoCompleto = Paths.get(uploadDir + nomeArquivo);

            dto.getImagemFile().transferTo(caminhoCompleto.toFile());
            dto.setImagem(nomeArquivo); // salva só o nome no banco
        }


        servicoService.atualizar(id, dto);
        return "redirect:/servicos";

    }

    @GetMapping("/delete/{id}")
    public String deletar(@PathVariable Long id) {
        servicoService.excluir(id);
        return "redirect:/servicos";
    }
}
