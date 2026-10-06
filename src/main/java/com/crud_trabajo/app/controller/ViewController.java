package com.crud_trabajo.app.controller;

import com.crud_trabajo.app.service.ClubService;
import com.crud_trabajo.app.service.EntrenadorService;
import com.crud_trabajo.app.service.JugadorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ClubService clubService;
    private final EntrenadorService entrenadorService;
    private final JugadorService jugadorService;

    public ViewController(ClubService clubService,
                          EntrenadorService entrenadorService,
                          JugadorService jugadorService) {
        this.clubService = clubService;
        this.entrenadorService = entrenadorService;
        this.jugadorService = jugadorService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clubes", clubService.findAll());
        model.addAttribute("entrenadores", entrenadorService.findAll());
        model.addAttribute("jugadores", jugadorService.findAll());
        return "index";
    }
}
