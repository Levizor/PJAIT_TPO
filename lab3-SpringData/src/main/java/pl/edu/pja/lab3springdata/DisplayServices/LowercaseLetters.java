package pl.edu.pja.lab3springdata.DisplayServices;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("lowercase")
public class LowercaseLetters implements DisplayService {
    public void display(String entry) {
        System.out.println(entry.toLowerCase());
    }
}
