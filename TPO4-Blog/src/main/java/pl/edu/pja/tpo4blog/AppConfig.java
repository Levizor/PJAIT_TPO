package pl.edu.pja.tpo4blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pja.tpo4blog.entities.Table;
import pl.edu.pja.tpo4blog.services.AddRole;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class AppConfig {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
