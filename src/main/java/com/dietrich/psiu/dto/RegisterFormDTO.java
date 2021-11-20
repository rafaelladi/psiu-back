package com.dietrich.psiu.dto;

import com.dietrich.psiu.validator.annotation.PasswordMatches;
import com.dietrich.psiu.validator.annotation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@Getter
@Setter
public class RegisterFormDTO extends FormDTO {
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String name;
}
