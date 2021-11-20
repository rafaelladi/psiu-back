package com.dietrich.psiu.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class FormDTO {
    @NotNull
    @NotEmpty
    private String password;
    private String confirmPassword;
}
