package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.UserProfileFormDTO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services.UserProfileService;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import jakarta.validation.Valid;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private static final Logger logger=LoggerFactory.getLogger(UserProfileController.class);
    private final UserProfileService service;
    private final MessageSource messages;
    private final String fixedEmail;
    public UserProfileController(UserProfileService service,MessageSource messages,
            @Value("${app.profile-email:admin@app.local}") String fixedEmail) {
        this.service=service; this.messages=messages; this.fixedEmail=fixedEmail;
    }
    @GetMapping public String profile() {return "redirect:/profile/edit";}
    @GetMapping("/edit") public String showProfileForm(Model model,Locale locale) {
        try {model.addAttribute("userProfileForm",service.getFormByEmail(fixedEmail));}
        catch(ResourceNotFoundException exception) {
            model.addAttribute("errorMessage",messages.getMessage("msg.user-controller.edit.notfound",null,locale));
        } catch(Exception exception) {
            logger.error("Error al cargar el perfil",exception);
            model.addAttribute("errorMessage",messages.getMessage("msg.userProfile.error",null,locale));
        }
        return "views/user-profile/user-profile-form";
    }
    @PostMapping("/update") public String updateProfile(
            @Valid @ModelAttribute("userProfileForm") UserProfileFormDTO dto,BindingResult result,
            @RequestParam(required=false) MultipartFile profileImageFile,
            RedirectAttributes flash,Locale locale) {
        if(result.hasErrors()) return "views/user-profile/user-profile-form";
        try {
            var current=service.getFormByEmail(fixedEmail);
            dto.setUserId(current.getUserId()); dto.setEmail(current.getEmail());
            service.updateProfile(dto,profileImageFile);
            flash.addFlashAttribute("successMessage",messages.getMessage("msg.userProfile.success",null,locale));
        } catch(ResourceNotFoundException exception) {
            flash.addFlashAttribute("errorMessage",messages.getMessage("msg.user-controller.edit.notfound",null,locale));
        } catch(InvalidFileException exception) {
            flash.addFlashAttribute("errorMessage",messages.getMessage("msg.userProfile.image.invalid",null,locale));
        } catch(Exception exception) {
            logger.error("Error al actualizar el perfil",exception);
            flash.addFlashAttribute("errorMessage",messages.getMessage("msg.userProfile.error",null,locale));
        }
        return "redirect:/profile/edit";
    }
}
