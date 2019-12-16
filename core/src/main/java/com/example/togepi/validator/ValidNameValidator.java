package com.example.togepi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public void initialize(ValidName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return Arrays.asList("John", "Hendro", "Henry").contains(name);
    }
}
