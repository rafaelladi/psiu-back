package com.dietrich.psiu.validator;

import com.dietrich.psiu.dto.FormDTO;
import com.dietrich.psiu.dto.organization.OrganizationNewDTO;
import com.dietrich.psiu.dto.user.PersonNewDTO;
import com.dietrich.psiu.validator.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if(obj instanceof FormDTO form) {
            return form.getPassword().equals(form.getConfirmPassword());
        }
        if(obj instanceof OrganizationNewDTO dto) {
            return dto.getAdminPassword().equals(dto.getAdminConfirmPassword());
        }
        if(obj instanceof PersonNewDTO dto) {
            return dto.getPassword().equals(dto.getConfirmPassword());
        }
        return false;
    }
}
