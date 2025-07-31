package pl.edu.pja.tpo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:external.yaml")
public class AppConfig {
    final String filename;

    public AppConfig(@Value("${external.filename}") String filename){
        this.filename = filename;
    }

    @Bean
    public String filename(){
        return filename;
    }

    @Bean
    public Scanner scanner() {
        return  new Scanner(System.in, "UTF-8");
    }
}
