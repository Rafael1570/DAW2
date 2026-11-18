package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.RoleFormDTO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services.RoleService;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.DuplicateResourceException;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService service;
    private final MessageSource messages;
    public RoleController(RoleService service, MessageSource messages) {this.service=service;this.messages=messages;}
    @GetMapping public String list(Model model) {model.addAttribute("roles",service.list());return "views/role/role-list";}
    @GetMapping("/new") public String newForm(Model model) {model.addAttribute("role",new RoleFormDTO());return "views/role/role-form";}
    @GetMapping("/edit") public String edit(@RequestParam Long id,Model model) {model.addAttribute("role",service.getForEdit(id));return "views/role/role-form";}
    @GetMapping("/detail") public String detail(@RequestParam Long id,Model model) {model.addAttribute("detail",service.getDetail(id));return "views/role/role-detail";}
    @PostMapping("/insert") public String create(@Valid @ModelAttribute("role") RoleFormDTO dto,BindingResult result) {
        if(result.hasErrors()) return "views/role/role-form";
        try {service.create(dto);}catch(DuplicateResourceException e) {result.rejectValue("name","msg.duplicateRole");return "views/role/role-form";}
        return "redirect:/roles";
    }
    @PostMapping("/update") public String update(@RequestParam Long id,@Valid @ModelAttribute("role") RoleFormDTO dto,BindingResult result) {
        if(result.hasErrors()) return "views/role/role-form";
        try {service.update(id,dto);}catch(DuplicateResourceException e) {result.rejectValue("name","msg.duplicateRole");return "views/role/role-form";}
        return "redirect:/roles";
    }
    @PostMapping("/delete") public String delete(@RequestParam Long id,RedirectAttributes flash,Locale locale) {
        try {service.delete(id);}catch(IllegalStateException e) {flash.addFlashAttribute("errorMessage",messages.getMessage("msg.roleInUse",null,locale));}
        return "redirect:/roles";
    }
}
