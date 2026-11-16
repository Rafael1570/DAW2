package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import jakarta.validation.Valid;
import java.util.Locale;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.ProvinceDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;
import org.springframework.context.MessageSource;
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
@RequestMapping("/provinces")
public class ProvinceController {
    private final ProvinceDAO provinces;
    private final RegionDAO regions;
    private final MessageSource messages;

    public ProvinceController(ProvinceDAO provinces, RegionDAO regions, MessageSource messages) {
        this.provinces = provinces;
        this.regions = regions;
        this.messages = messages;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("provinces", provinces.findAll());
        return "views/province/province-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("province", new Province());
        form(model, false);
        return "views/province/province-form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam long id, Model model, RedirectAttributes redirect, Locale locale) {
        return provinces.findById(id).map(province -> {
            model.addAttribute("province", province);
            form(model, true);
            return "views/province/province-form";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("errorMessage", msg("msg.province.notfound", locale));
            return "redirect:/provinces";
        });
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("province") Province province, BindingResult binding,
                         Model model, RedirectAttributes redirect, Locale locale) {
        validate(province, binding, false, locale);
        if (binding.hasErrors()) {
            form(model, false);
            return "views/province/province-form";
        }
        provinces.insert(province);
        redirect.addFlashAttribute("successMessage", msg("msg.province.created", locale));
        return "redirect:/provinces";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("province") Province province, BindingResult binding,
                         Model model, RedirectAttributes redirect, Locale locale) {
        if (province.getId() == null || provinces.findById(province.getId()).isEmpty()) {
            redirect.addFlashAttribute("errorMessage", msg("msg.province.notfound", locale));
            return "redirect:/provinces";
        }
        validate(province, binding, true, locale);
        if (binding.hasErrors()) {
            form(model, true);
            return "views/province/province-form";
        }
        provinces.update(province);
        redirect.addFlashAttribute("successMessage", msg("msg.province.updated", locale));
        return "redirect:/provinces";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id, RedirectAttributes redirect, Locale locale) {
        provinces.delete(id);
        redirect.addFlashAttribute("successMessage", msg("msg.province.deleted", locale));
        return "redirect:/provinces";
    }

    private void form(Model model, boolean editing) {
        model.addAttribute("regions", regions.findAll());
        model.addAttribute("editing", editing);
    }

    private void validate(Province province, BindingResult binding, boolean editing, Locale locale) {
        if (!binding.hasFieldErrors("code") && (editing
                ? provinces.existsByCodeAndNotId(province.getCode(), province.getId())
                : provinces.existsByCode(province.getCode()))) {
            binding.rejectValue("code", "duplicate", msg("msg.province.duplicate", locale));
        }
        if (province.getRegion() != null && province.getRegion().getId() != null
                && regions.findById(province.getRegion().getId()).isEmpty()) {
            binding.rejectValue("region.id", "invalid", msg("msg.province.region.invalid", locale));
        }
    }

    private String msg(String code, Locale locale) {
        return messages.getMessage(code, null, locale);
    }
}
