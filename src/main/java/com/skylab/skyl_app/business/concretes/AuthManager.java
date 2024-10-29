package com.skylab.skyl_app.business.concretes;

import com.skylab.skyl_app.business.abstracts.AuthService;
import com.skylab.skyl_app.business.abstracts.EmailActivationService;
import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.core.exceptions.EmailIsNotValidException;
import com.skylab.skyl_app.core.exceptions.UserAlreadyExistsException;
import com.skylab.skyl_app.core.security.JwtService;
import com.skylab.skyl_app.entities.Role;
import com.skylab.skyl_app.entities.User;
import com.skylab.skyl_app.entities.dtos.LoginDto;
import com.skylab.skyl_app.entities.dtos.RegisterDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthManager implements AuthService {

    private final UserService userService;

    private final EmailActivationService emailActivationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthManager(EmailActivationService emailActivationService, UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.emailActivationService = emailActivationService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public void register(RegisterDto registerDto) {
        if (userService.checkIfUserExistsByMail(registerDto.getEmail())) {
            throw new UserAlreadyExistsException("User with "+registerDto.getEmail()+ " email already exists");
        }

        if (!CheckIfEmailValid(registerDto.getEmail())) {
            throw new EmailIsNotValidException("Email is not valid");
        }

        User user = new User();

        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setAuthorities(Set.of(Role.ROLE_USER));
        user.setEnabled(false);

        user = userService.addUser(user);
        if (user != null){
            emailActivationService.sendActivationEmail(user);
        }

    }

    private boolean CheckIfEmailValid(String email) {

        if (email.contains("@")) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String login(LoginDto loginDto) {

        if (loginDto.getEmail() == null) {
            throw new NullPointerException("Email cannot be null");
        }

        if (loginDto.getPassword() == null) {
            throw new NullPointerException("Password cannot be null");
        }

        if (!userService.checkIfUserExistsByMail(loginDto.getEmail())) {
          throw new SecurityException("User with email "+loginDto.getEmail()+" does not exist");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        if (authentication.isAuthenticated()) {
            var token = jwtService.generateToken(loginDto.getEmail());

            return token;
        } else {
            throw new SecurityException("Invalid username or password");
        }



    }
}
