package pl.edu.pja.tpo2.DisplayServices;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo2.Entry;

@Service
@Profile("uppercase")
public class UppercaseLetters implements DisplayService {
    @Override
    public void display(String entry) {
        System.out.println(entry.toUpperCase());
    }
}
