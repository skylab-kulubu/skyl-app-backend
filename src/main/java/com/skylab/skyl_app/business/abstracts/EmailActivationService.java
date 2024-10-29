package com.skylab.skyl_app.business.abstracts;

import com.skylab.skyl_app.entities.User;

public interface EmailActivationService {

    boolean sendActivationEmail(User user);

    boolean activateUser(String activationCode);


}
