package pl.edu.pja.lab3springdata.DisplayServices;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Primary
@Service
@Profile("default")
public class Default implements DisplayService {
    @Override
    public void display(String entry) {
        System.out.println(entry);
    }
}
