package pl.edu.pja.sladan.tpo_02.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.pja.sladan.tpo_02.profiles.ConsolePrinter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        MyClass myClass = context.getBean(MyClass.class);
        System.out.println(myClass);

        System.out.println(context.getBean(MyClass2.class));
        System.out.println(context.getBean(MyClass3.class));
    }

}
