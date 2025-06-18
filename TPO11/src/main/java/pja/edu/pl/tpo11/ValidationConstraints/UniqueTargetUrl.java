package pja.edu.pl.tpo11.ValidationConstraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueTargetUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTargetUrl {
    String message() default "{url.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
