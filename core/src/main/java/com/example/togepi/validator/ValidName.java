package com.example.togepi.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidNameValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidName {

    String message() default "Name is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
