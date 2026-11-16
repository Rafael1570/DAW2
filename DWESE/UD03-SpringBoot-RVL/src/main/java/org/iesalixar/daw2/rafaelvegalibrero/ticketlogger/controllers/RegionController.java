package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import jakarta.validation.Valid;
import java.util.Locale;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/regions")
public class RegionController {
    private final RegionDAO regions;
    private final MessageSource messages;

    public RegionController(RegionDAO regions, MessageSource messages) {
        this.regions = regions;
        this.messages = messages;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("regions", regions.findAll());
        return "views/region/region-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("region", new Region());
        model.addAttribute("editing", false);
        return "views/region/region-form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam long id, Model model, RedirectAttributes redirect, Locale locale) {
        return regions.findById(id).map(region -> {
            model.addAttribute("region", region);
            model.addAttribute("editing", true);
            return "views/region/region-form";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("errorMessage", msg("msg.region.notfound", locale));
            return "redirect:/regions";
        });
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("region") Region region, BindingResult binding,
                         Model model, RedirectAttributes redirect, Locale locale) {
        if (!binding.hasFieldErrors("code") && regions.existsByCode(region.getCode())) {
            binding.rejectValue("code", "duplicate", msg("msg.region.duplicate", locale));
        }
        if (binding.hasErrors()) {
            model.addAttribute("editing", false);
            return "views/region/region-form";
        }
        regions.insert(region);
        redirect.addFlashAttribute("successMessage", msg("msg.region.created", locale));
        return "redirect:/regions";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("region") Region region, BindingResult binding,
                         Model model, RedirectAttributes redirect, Locale locale) {
        if (region.getId() == null || regions.findById(region.getId()).isEmpty()) {
            redirect.addFlashAttribute("errorMessage", msg("msg.region.notfound", locale));
            return "redirect:/regions";
        }
        if (!binding.hasFieldErrors("code") && regions.existsByCodeAndNotId(region.getCode(), region.getId())) {
            binding.rejectValue("code", "duplicate", msg("msg.region.duplicate", locale));
        }
        if (binding.hasErrors()) {
            model.addAttribute("editing", true);
            return "views/region/region-form";
        }
        regions.update(region);
        redirect.addFlashAttribute("successMessage", msg("msg.region.updated", locale));
        return "redirect:/regions";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id, RedirectAttributes redirect, Locale locale) {
        try {
            regions.delete(id);
            redirect.addFlashAttribute("successMessage", msg("msg.region.deleted", locale));
        } catch (DataIntegrityViolationException ex) {
            redirect.addFlashAttribute("errorMessage", msg("msg.region.inuse", locale));
        }
        return "redirect:/regions";
    }

    private String msg(String code, Locale locale) {
        return messages.getMessage(code, null, locale);
    }
}
