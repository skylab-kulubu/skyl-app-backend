package com.skylab.skyl_app.business.abstracts;

import com.skylab.skyl_app.entities.User;
import com.skylab.skyl_app.entities.dtos.ChangePasswordDto;
import com.skylab.skyl_app.entities.dtos.RegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User addUser(User user);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(int id);

    User getUserByActivationCode(String activationCode);

    boolean activateUserById(int id);

    boolean checkIfUserExistsByMail(String email);

    User getLoggedInUser();

    List<Optional<User>> getUsers();

    void changePassword(ChangePasswordDto changePasswordDto);
}
