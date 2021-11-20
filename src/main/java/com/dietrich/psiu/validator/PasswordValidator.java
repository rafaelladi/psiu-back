package com.dietrich.psiu.validator;

import com.dietrich.psiu.dto.FormDTO;
import com.dietrich.psiu.validator.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if(obj instanceof FormDTO) {
            FormDTO form = (FormDTO) obj;
            return form.getPassword().equals(form.getConfirmPassword());
        }
        return false;
    }
}
