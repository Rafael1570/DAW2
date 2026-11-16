package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("author", "Rafael Vega Librero");
        return "index";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(defaultValue = "mundo") String name) {
        return "Hola, " + name + " — Ticket Logger de Rafael Vega Librero";
    }
}
