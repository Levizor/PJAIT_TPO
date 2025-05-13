package pl.edu.pja.tpo_06.service;

import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo_06.DTO.Person;
import pl.edu.pja.tpo_06.DTO.FormData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FakeDataService {
    public List<Person> generatePeople(FormData formData) {
        Faker faker = new Faker(new Locale(formData.getLanguage()));
        List<Person> people = new ArrayList<>();

        var additional = formData.getAdditionalAttributes();

        for(int i = 0; i < formData.getNumOfEntries(); i++){
            Person person = new Person();
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setDateOfBirth(faker.date().birthday(18, 60).toLocalDateTime().toLocalDate());

            if (additional != null) {
                if (additional.contains("address")) person.setAddress(faker.address().fullAddress());
                if (additional.contains("email")) person.setEmail(faker.internet().emailAddress());
                if (additional.contains("phoneNumber")) person.setPhoneNumber(faker.phoneNumber().phoneNumber());
                if (additional.contains("country")) person.setCountry(faker.country().name());
                if (additional.contains("university")) person.setUniversity(faker.university().name());
                if (additional.contains("job")) person.setJob(faker.job().title());
                if (additional.contains("company")) person.setCompany(faker.company().name());
                if (additional.contains("isbn")) person.setIsbn(faker.code().isbnRegistrant());
            }
            people.add(person);
        }

        return people;
    }

}
