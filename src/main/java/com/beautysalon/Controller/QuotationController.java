package com.beautysalon.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotationController
{
    @GetMapping("/api/cotacao")
    public static String getCotacaoDolar() {
        return QuotationController.getCotacaoDolar();
    }
}
