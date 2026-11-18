package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.controllers;

import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(ResourceNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
    public String missing(Model model) {model.addAttribute("errorKey","msg.notFound");return "error/application";}
    @ExceptionHandler(MaxUploadSizeExceededException.class) @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public String large(Model model) {model.addAttribute("errorKey","msg.userProfile.image.tooLarge");return "error/application";}
}
