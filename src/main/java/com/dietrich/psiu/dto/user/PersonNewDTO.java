package com.dietrich.psiu.dto.user;

import com.dietrich.psiu.validator.annotation.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@PasswordMatches
public class PersonNewDTO {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
