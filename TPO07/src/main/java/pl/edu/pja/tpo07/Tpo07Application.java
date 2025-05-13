package pl.edu.pja.tpo07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Tpo07Application {

    public static void main(String[] args) {
        SpringApplication.run(Tpo07Application.class, args);
    }

}
