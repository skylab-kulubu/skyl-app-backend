package com.skylab.skyl_app.api.controllers;

import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.entities.User;
import com.skylab.skyl_app.entities.dtos.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<Optional<User>>> getAllUsers() {
        List<Optional<User>> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

}
