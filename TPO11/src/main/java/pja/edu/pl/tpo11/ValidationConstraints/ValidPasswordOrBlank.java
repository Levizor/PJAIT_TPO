package pja.edu.pl.tpo11.ValidationConstraints;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidPasswordValidatorOrBlank.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswordOrBlank {
    String message() default "Invalid password";
    Class[] groups() default {};
    Class[] payload() default {};
}
