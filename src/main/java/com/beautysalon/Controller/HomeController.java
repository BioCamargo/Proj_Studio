package com.beautysalon.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("contentPage", "home/indexContent");
        model.addAttribute("content", "home/welcome"); // Página de boas-vindas
        model.addAttribute("pageTitle", "Beauty Salon - Home");
        return "home/index";

    }
    @GetMapping("/home")
    public String homePage(Model model) {
        return home(model); // Reutiliza a mesma lógica da rota raiz
    }
}
