package pl.edu.pja.tpo08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TPO08Application {

    public static void main(String[] args) {
        SpringApplication.run(TPO08Application.class, args);
    }

}
