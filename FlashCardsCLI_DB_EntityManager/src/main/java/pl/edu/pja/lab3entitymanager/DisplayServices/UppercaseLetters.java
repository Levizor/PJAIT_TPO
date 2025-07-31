package pl.edu.pja.lab3entitymanager.DisplayServices;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("uppercase")
public class UppercaseLetters implements DisplayService {
    @Override
    public void display(String entry) {
        System.out.println(entry.toUpperCase());
    }
}
