package com.dietrich.psiu.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginFormDTO {
    @NotNull
    @NotEmpty
    private String email;


    @NotNull
    @NotEmpty
    private String password;
}
