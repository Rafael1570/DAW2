package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import java.util.Locale;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.ProvinceDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Province;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.entities.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);
    private final ProvinceDAO provinceDAO;
    private final RegionDAO regionDAO;
    private final MessageSource messageSource;

    public ProvinceController(ProvinceDAO provinceDAO, RegionDAO regionDAO, MessageSource messageSource) {
        this.provinceDAO = provinceDAO;
        this.regionDAO = regionDAO;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String listProvinces(Model model, Locale locale) {
        try {
            model.addAttribute("provinces", provinceDAO.findAll());
        } catch (Exception exception) {
            logger.error("Error listing provinces", exception);
            model.addAttribute("errorMessage", messageSource.getMessage("msg.province-controller.list.error", null, locale));
        }
        return "views/province/province-list";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("province", new Province(null, "", "", new Region()));
        model.addAttribute("regions", regionDAO.findAll());
        return "views/province/province-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            var province = provinceDAO.findById(id);
            if (province.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.edit.notfound", null, locale));
                return "redirect:/provinces";
            }
            model.addAttribute("province", province.get());
            model.addAttribute("regions", regionDAO.findAll());
            return "views/province/province-form";
        } catch (Exception exception) {
            logger.error("Error loading province {}", id, exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.edit.error", null, locale));
            return "redirect:/provinces";
        }
    }

    @PostMapping("/insert")
    public String insertProvince(@Validated @ModelAttribute("province") Province province, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionDAO.findAll());
            return "views/province/province-form";
        }
        try {
            if (provinceDAO.existsProvinceByCode(province.getCode())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.insert.codeExist", null, locale));
                return "redirect:/provinces/new";
            }
            provinceDAO.insertProvince(province);
        } catch (Exception exception) {
            logger.error("Error inserting province {}", province.getCode(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.insert.error", null, locale));
        }
        return "redirect:/provinces";
    }

    @PostMapping("/update")
    public String updateProvince(@Validated @ModelAttribute("province") Province province, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionDAO.findAll());
            return "views/province/province-form";
        }
        try {
            if (provinceDAO.existsProvinceByCodeAndNotId(province.getCode(), province.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.update.codeExist", null, locale));
                return "redirect:/provinces/edit?id=" + province.getId();
            }
            provinceDAO.updateProvince(province);
        } catch (Exception exception) {
            logger.error("Error updating province {}", province.getId(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.update.error", null, locale));
        }
        return "redirect:/provinces";
    }

    @PostMapping("/delete")
    public String deleteProvince(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            provinceDAO.deleteProvince(id);
        } catch (Exception exception) {
            logger.error("Error deleting province {}", id, exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.delete.error", null, locale));
        }
        return "redirect:/provinces";
    }
}
