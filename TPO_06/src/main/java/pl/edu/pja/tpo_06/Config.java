package pl.edu.pja.tpo_06;

import net.datafaker.Faker;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class Config {
    @Bean
    public Faker faker(){
        return new Faker();
    }
}
