package com.skylab.skyl_app.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;

}
