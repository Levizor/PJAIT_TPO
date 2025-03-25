package pl.edu.pja.sladan.tpo_02.profiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        ConsolePrinter consolePrinter = context.getBean(ConsolePrinter.class);
        consolePrinter.printMessage();
    }

}
