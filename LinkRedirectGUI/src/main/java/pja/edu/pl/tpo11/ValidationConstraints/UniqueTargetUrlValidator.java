package pja.edu.pl.tpo11.ValidationConstraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pja.edu.pl.tpo11.Repositories.RedirectLinkRepository;

@Service
public class UniqueTargetUrlValidator implements ConstraintValidator<UniqueTargetUrl, String> {

    private RedirectLinkRepository linkRepository;

    public UniqueTargetUrlValidator(RedirectLinkRepository repository){
        this.linkRepository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return !linkRepository.existsByTargetUrlEquals(value);
    }
}
