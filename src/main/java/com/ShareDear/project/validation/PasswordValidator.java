package com.ShareDear.project.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Constraint(validatedBy = PasswordValidatorConstraint.class)
public @interface PasswordValidator {

    String message();

    Class<?>[] groups() default { } ;

    Class<? extends Payload>[] payload() default { };
}
