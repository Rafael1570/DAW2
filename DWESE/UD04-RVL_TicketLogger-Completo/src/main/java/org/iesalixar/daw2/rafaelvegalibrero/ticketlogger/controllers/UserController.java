package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services.UserService;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import jakarta.validation.Valid;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger=LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final MessageSource messages;
    public UserController(UserService service,MessageSource messages) {this.service=service;this.messages=messages;}
    @GetMapping public String list(@RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,@RequestParam(defaultValue="email") String sortField,
            @RequestParam(defaultValue="asc") String sortDir,Model model,Locale locale) {
        UserPageDTO result;
        try {result=service.list(page,size,sortField,sortDir);}
        catch(Exception exception) {
            logger.error("Error al listar usuarios",exception);
            result=new UserPageDTO(List.of(),0,10,0,0,"email","asc");
            model.addAttribute("errorMessage",messages.getMessage("msg.user-controller.error",null,locale));
        }
        model.addAttribute("users",result.getUsers());
        model.addAttribute("currentPage",result.getPage());model.addAttribute("pageSize",result.getSize());
        model.addAttribute("offset",result.getPage()*result.getSize());model.addAttribute("totalElements",result.getTotalElements());
        model.addAttribute("totalPages",result.getTotalPages());model.addAttribute("sortField",result.getSortField());
        model.addAttribute("sortDir",result.getSortDir());
        model.addAttribute("reverseSortDir","asc".equals(result.getSortDir())?"desc":"asc");
        return "views/user/user-list";
    }
    @GetMapping("/detail") public String detail(@RequestParam Long id,Model model,RedirectAttributes flash,Locale locale) {
        try {model.addAttribute("user",service.getDetail(id));return "views/user/user-detail";}
        catch(ResourceNotFoundException exception) {missing(flash,locale);return "redirect:/users";}
    }
    @GetMapping("/new") public String createForm(Model model) {model.addAttribute("user",new UserCreateDTO());return form(model);}
    @GetMapping("/edit") public String editForm(@RequestParam Long id,Model model,RedirectAttributes flash,Locale locale) {
        try {model.addAttribute("user",service.getForEdit(id));return form(model);}
        catch(ResourceNotFoundException exception) {missing(flash,locale);return "redirect:/users";}
    }
    private String form(Model model) {model.addAttribute("allRoles",service.roles());return "views/user/user-form";}
    private void missing(RedirectAttributes flash,Locale locale) {
        flash.addFlashAttribute("errorMessage",messages.getMessage("msg.user-controller.edit.notfound",null,locale));
    }
    @PostMapping("/insert") public String create(@Valid @ModelAttribute("user") UserCreateDTO dto,
            BindingResult result,Model model,RedirectAttributes flash,Locale locale) {
        if(result.hasErrors()) return form(model);
        try {service.create(dto);}
        catch(DuplicateResourceException exception) {result.rejectValue("email","msg.duplicateEmail");return form(model);}
        catch(ResourceNotFoundException exception) {result.rejectValue("roleIds","msg.invalidRoles");return form(model);}
        flash.addFlashAttribute("successMessage",messages.getMessage("msg.user-controller.insert.success",null,locale));
        return "redirect:/users";
    }
    @PostMapping("/update") public String update(@Valid @ModelAttribute("user") UserUpdateDTO dto,
            BindingResult result,Model model,RedirectAttributes flash,Locale locale) {
        if(result.hasErrors()) return form(model);
        try {service.update(dto);}
        catch(DuplicateResourceException exception) {result.rejectValue("email","msg.duplicateEmail");return form(model);}
        catch(ResourceNotFoundException exception) {
            if("role".equals(exception.getResource())) {result.rejectValue("roleIds","msg.invalidRoles");return form(model);}
            missing(flash,locale);return "redirect:/users";
        }
        flash.addFlashAttribute("successMessage",messages.getMessage("msg.user-controller.update.success",null,locale));
        return "redirect:/users";
    }
    @PostMapping("/delete") public String delete(@RequestParam Long id,RedirectAttributes flash,Locale locale) {
        try {service.delete(id);flash.addFlashAttribute("successMessage",messages.getMessage("msg.user-controller.delete.success",null,locale));}
        catch(ResourceNotFoundException exception) {missing(flash,locale);}
        return "redirect:/users";
    }
}
