package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import java.util.Locale;
import java.util.Set;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.web.Pagination;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.RegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/regions")
public class RegionController {
    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);
    private final RegionDAO regionDAO;
    private final MessageSource messageSource;

    public RegionController(RegionDAO regionDAO, MessageSource messageSource) {
        this.regionDAO = regionDAO;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String listRegions(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "name") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDir,
                               Model model, Locale locale) {
        try {
            var pagination = Pagination.of(page, size, regionDAO.countRegions(), sortField, sortDir, Set.of("id", "code", "name"));
            pagination.addTo(model);
            model.addAttribute("regions", RegionMapper.toDTOList(regionDAO.listRegionsPage(
                    pagination.page(), pagination.size(), pagination.sortField(), pagination.sortDir())));
        } catch (Exception exception) {
            Pagination.of(0, 10, 0, "name", "asc", Set.of("id", "code", "name")).addTo(model);
            model.addAttribute("regions", java.util.List.of());
            logger.error("Error listing regions", exception);
            model.addAttribute("errorMessage", messageSource.getMessage("msg.region-controller.list.error", null, locale));
        }
        return "views/region/region-list";
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        return regionDAO.findById(id).map(entity -> {
            model.addAttribute("region", RegionMapper.toDetailDTO(entity));
            return "views/region/region-detail";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.detail.notFound", null, locale));
            return "redirect:/regions";
        });
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("region", new RegionCreateDTO());
        return "views/region/region-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            return regionDAO.findById(id).map(region -> {
                model.addAttribute("region", RegionMapper.toUpdateDTO(region));
                return "views/region/region-form";
            }).orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.edit.notfound", null, locale));
                return "redirect:/regions";
            });
        } catch (Exception exception) {
            logger.error("Error loading region {}", id, exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.edit.error", null, locale));
            return "redirect:/regions";
        }
    }

    @PostMapping("/insert")
    public String insertRegion(@Validated @ModelAttribute("region") RegionCreateDTO region, BindingResult result,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) return "views/region/region-form";
        try {
            if (regionDAO.existsRegionByCode(region.getCode())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.insert.codeExist", null, locale));
                return "redirect:/regions/new";
            }
            regionDAO.insertRegion(RegionMapper.toEntity(region));
        } catch (Exception exception) {
            logger.error("Error inserting region {}", region.getCode(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.insert.error", null, locale));
        }
        return "redirect:/regions";
    }

    @PostMapping("/update")
    public String updateRegion(@Validated @ModelAttribute("region") RegionUpdateDTO region, BindingResult result,
                               RedirectAttributes redirectAttributes, Locale locale) {
        if (result.hasErrors()) return "views/region/region-form";
        try {
            if (regionDAO.findById(region.getId()).isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.edit.notfound", null, locale));
                return "redirect:/regions";
            }
            if (regionDAO.existsRegionByCodeAndNotId(region.getCode(), region.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.update.codeExist", null, locale));
                return "redirect:/regions/edit?id=" + region.getId();
            }
            regionDAO.updateRegion(RegionMapper.toEntity(region));
        } catch (Exception exception) {
            logger.error("Error updating region {}", region.getId(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.update.error", null, locale));
        }
        return "redirect:/regions";
    }

    @PostMapping("/delete")
    public String deleteRegion(@RequestParam Long id, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            regionDAO.deleteRegion(id);
        } catch (Exception exception) {
            logger.error("Error deleting region {}", id, exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.region-controller.delete.error", null, locale));
        }
        return "redirect:/regions";
    }
}
