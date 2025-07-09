package com.skylab.skyl_app.api.controllers;

import com.skylab.skyl_app.business.abstracts.AuthService;
import com.skylab.skyl_app.entities.dtos.LoginDto;
import com.skylab.skyl_app.entities.dtos.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        var result = authService.login(loginDto);
        return ResponseEntity.ok(result);
    }

}
