package pl.edu.pja.tpo2.DisplayServices;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo2.Entry;

@Service
@Profile("lowercase")
public class LowercaseLetters implements DisplayService {
    public void display(String entry) {
        System.out.println(entry.toLowerCase());
    }
}
