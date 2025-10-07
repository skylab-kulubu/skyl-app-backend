package com.skylab.skyl_app.business.abstracts;

import com.skylab.skyl_app.entities.User;
import com.skylab.skyl_app.entities.dtos.LoginDto;
import com.skylab.skyl_app.entities.dtos.RegisterDto;

public interface AuthService {

    void register(RegisterDto registerDto);

    String login(LoginDto loginDto);

}
