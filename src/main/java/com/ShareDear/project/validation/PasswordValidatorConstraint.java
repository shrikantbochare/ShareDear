package com.ShareDear.project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorConstraint implements ConstraintValidator<PasswordValidator,String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@_])[A-Za-z\\d@_]{8,}$");
        Matcher matcher = pattern.matcher(value);


        try
        {
            return matcher.matches();
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
