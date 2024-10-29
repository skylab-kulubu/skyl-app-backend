package com.skylab.skyl_app.api.controllers;

import com.skylab.skyl_app.business.abstracts.EmailActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailActivations")
public class EmailActivationsController {

    private final EmailActivationService emailActivationService;

    public EmailActivationsController(EmailActivationService emailActivationService) {
        this.emailActivationService = emailActivationService;
    }


    @GetMapping("/{activationCode}")
    public ResponseEntity<?> activateAccount(@PathVariable String activationCode){
        emailActivationService.activateUser(activationCode);
        return ResponseEntity.ok().build();
    }
}
