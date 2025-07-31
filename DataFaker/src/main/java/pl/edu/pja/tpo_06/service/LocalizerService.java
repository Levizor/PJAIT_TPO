package pl.edu.pja.tpo_06.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo_06.DTO.Headers;
import pl.edu.pja.tpo_06.DTO.FormData;

import java.util.Locale;

@Service
public class LocalizerService {
    private MessageSource messageSource;

    public LocalizerService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Headers getLocalizedHeaders(FormData formData) {
        Headers headers = new Headers();
        Locale locale = new Locale(formData.getLanguage());
        var additional = formData.getAdditionalAttributes();

        headers.setFirstName(messageSource.getMessage("header.firstName", null, locale));
        headers.setLastName(messageSource.getMessage("header.lastName", null, locale));
        headers.setDateOfBirth(messageSource.getMessage("header.dateOfBirth", null, locale));

        if (additional != null) {
            if (additional.contains("address")) headers.setAddress(messageSource.getMessage("header.address", null, locale));
            if (additional.contains("email")) headers.setEmail(messageSource.getMessage("header.email", null, locale));
            if (additional.contains("phoneNumber")) headers.setPhoneNumber(messageSource.getMessage("header.phoneNumber", null, locale));
            if (additional.contains("country")) headers.setCountry(messageSource.getMessage("header.country", null, locale));
            if (additional.contains("university")) headers.setUniversity(messageSource.getMessage("header.university", null, locale));
            if (additional.contains("job")) headers.setJob(messageSource.getMessage("header.job", null, locale));
            if (additional.contains("company")) headers.setCompany(messageSource.getMessage("header.company", null, locale));
            if (additional.contains("isbn")) headers.setIsbn(messageSource.getMessage("header.isbn", null, locale));
        }

        return headers;
    }
}