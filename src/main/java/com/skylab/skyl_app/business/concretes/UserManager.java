package com.skylab.skyl_app.business.concretes;

import com.skylab.skyl_app.business.abstracts.EmailActivationService;
import com.skylab.skyl_app.business.abstracts.UserService;
import com.skylab.skyl_app.core.exceptions.UserDoesntExistsException;
import com.skylab.skyl_app.dataAccess.UserDao;
import com.skylab.skyl_app.entities.User;
import com.skylab.skyl_app.entities.dtos.ChangePasswordDto;
import com.skylab.skyl_app.entities.dtos.RegisterDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public UserManager(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDao.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        var userResult = userDao.findByEmail(email);

        if (userResult.isEmpty()) {
            throw new UserDoesntExistsException("User with email: " + email + " doesn't exists");
        }

        return userResult;
    }


    @Override
    public Optional<User> getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByActivationCode(String activationCode) {
        return null;
    }

    @Override
    public boolean activateUserById(int id) {
        var user = getUserById(id);

        if (user.isEmpty()) {
            throw new UserDoesntExistsException("User with id: " + id + " doesn't exists");
        }

        user.get().setEnabled(true);

        userDao.save(user.get());

        return true;
    }

    @Override
    public boolean checkIfUserExistsByMail(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal()=="anonymousUser"){
            throw new UserDoesntExistsException("User not logged in");
        }

        var userMail = authentication.getName();
        return getUserByEmail(userMail).get();
    }

    @Override
    public List<Optional<User>> getUsers() {
        var users = userDao.findAll();
        if (users.isEmpty()) {
            throw new UserDoesntExistsException("No users found");
        }

        return users.stream().map(Optional::of).toList();
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        var user = getLoggedInUser();

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            throw new UserDoesntExistsException("Old password is not correct"); //change exception later
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userDao.save(user);

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username).get();
    }
}
