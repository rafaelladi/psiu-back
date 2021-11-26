package com.dietrich.psiu.dto.organization;

import com.dietrich.psiu.validator.annotation.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@PasswordMatches
@Getter
@Setter
public class OrganizationNewDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String adminEmail;
    @NotEmpty
    private String adminName;
    @NotEmpty
    private String adminPassword;
    @NotEmpty
    private String adminConfirmPassword;
}
