package pja.edu.pl.tpo11.ValidationConstraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class ValidPasswordValidatorOrBlank implements ConstraintValidator<ValidPasswordOrBlank, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context){
        if (password == null) return true;
        if (password.isBlank()) return true;

        List<String> violationKeys = new ArrayList<>();

        if(!password.matches("^(?:.*[A-Z]){2,}.*$")){
            violationKeys.add("validPassword.uppercase");
        }

        if(!password.matches(".*[a-z].*")){
            violationKeys.add("validPassword.lowercase");
        }

        if(!password.matches("^(?:.*\\d){3,}.*")){
            violationKeys.add("validPassword.digits");
        }

        if(!password.matches("^(?:.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]){4,}.*")){
            violationKeys.add("validPassword.specialCharacters");
        }

        if (password.length() < 10){
            violationKeys.add("validPassword.length");
        }

        if (!violationKeys.isEmpty()){
            context.disableDefaultConstraintViolation();
            for (String key: violationKeys){
                context.buildConstraintViolationWithTemplate(String.format("{%s}", key)).addConstraintViolation();
            }
            return false;
        }

        return true;
    }

}
