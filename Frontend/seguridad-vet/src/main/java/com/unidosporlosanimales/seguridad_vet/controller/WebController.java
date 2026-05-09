package com.unidosporlosanimales.seguridad_vet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Página pública (Inicio)
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Página pública (Login)
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Página privada (Dashboard)
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}