package pl.edu.pja.tpo_07;

import com.google.googlejavaformat.java.Formatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Formatter formatter(){
        return new Formatter();
    }
}
