package pl.edu.pja.sladan.tpo_02.dependency_injection_container;

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

        Arrays.stream(context.getBeanDefinitionNames()).map(context::getBean).forEach(System.out::println);
    }

}
