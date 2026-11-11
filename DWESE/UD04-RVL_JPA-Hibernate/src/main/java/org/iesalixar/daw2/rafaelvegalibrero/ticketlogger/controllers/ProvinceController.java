package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import java.util.Locale;
import java.util.Set;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.web.Pagination;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.ProvinceDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.ProvinceMapper;
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
    public String listProvinces(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "name") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDir,
                               Model model, Locale locale) {
        try {
            var pagination = Pagination.of(page, size, provinceDAO.countProvinces(), sortField, sortDir, Set.of("id", "code", "name", "regionName"));
            pagination.addTo(model);
            model.addAttribute("provinces", ProvinceMapper.toDTOList(provinceDAO.listProvincesPage(
                    pagination.page(), pagination.size(), pagination.sortField(), pagination.sortDir())));
        } catch (Exception exception) {
            Pagination.of(0, 10, 0, "name", "asc", Set.of("id", "code", "name", "regionName")).addTo(model);
            model.addAttribute("provinces", java.util.List.of());
            logger.error("Error listing provinces", exception);
            model.addAttribute("errorMessage", messageSource.getMessage("msg.province-controller.list.error", null, locale));
        }
        return "views/province/province-list";
    }

    @GetMapping("/detail")
    public String showDetail(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        return provinceDAO.findById(id).map(entity -> {
            model.addAttribute("province", ProvinceMapper.toDetailDTO(entity));
            return "views/province/province-detail";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.detail.notFound", null, locale));
            return "redirect:/provinces";
        });
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("province", new ProvinceCreateDTO());
        model.addAttribute("regions", org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.RegionMapper.toDTOList(regionDAO.findAll()));
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
            model.addAttribute("province", ProvinceMapper.toUpdateDTO(province.get()));
            model.addAttribute("regions", org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.RegionMapper.toDTOList(regionDAO.findAll()));
            return "views/province/province-form";
        } catch (Exception exception) {
            logger.error("Error loading province {}", id, exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.edit.error", null, locale));
            return "redirect:/provinces";
        }
    }

    @PostMapping("/insert")
    public String insertProvince(@Validated @ModelAttribute("province") ProvinceCreateDTO province, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (!result.hasFieldErrors("regionId") && regionDAO.findById(province.getRegionId()).isEmpty()) {
            result.rejectValue("regionId", "msg.province.region.notNull");
        }
        if (result.hasErrors()) {
            model.addAttribute("regions", org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.RegionMapper.toDTOList(regionDAO.findAll()));
            return "views/province/province-form";
        }
        try {
            if (provinceDAO.existsProvinceByCode(province.getCode())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.insert.codeExist", null, locale));
                return "redirect:/provinces/new";
            }
            provinceDAO.insertProvince(ProvinceMapper.toEntity(province, regionDAO.findById(province.getRegionId()).orElseThrow()));
        } catch (Exception exception) {
            logger.error("Error inserting province {}", province.getCode(), exception);
            redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.insert.error", null, locale));
        }
        return "redirect:/provinces";
    }

    @PostMapping("/update")
    public String updateProvince(@Validated @ModelAttribute("province") ProvinceUpdateDTO province, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes, Locale locale) {
        if (!result.hasFieldErrors("regionId") && regionDAO.findById(province.getRegionId()).isEmpty()) {
            result.rejectValue("regionId", "msg.province.region.notNull");
        }
        if (result.hasErrors()) {
            model.addAttribute("regions", org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.RegionMapper.toDTOList(regionDAO.findAll()));
            return "views/province/province-form";
        }
        try {
            if (provinceDAO.findById(province.getId()).isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.edit.notfound", null, locale));
                return "redirect:/provinces";
            }
            if (provinceDAO.existsProvinceByCodeAndNotId(province.getCode(), province.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", messageSource.getMessage("msg.province-controller.update.codeExist", null, locale));
                return "redirect:/provinces/edit?id=" + province.getId();
            }
            provinceDAO.updateProvince(ProvinceMapper.toEntity(province, regionDAO.findById(province.getRegionId()).orElseThrow()));
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
