package pl.edu.pja.tpo4books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Tpo4BooksApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Tpo4BooksApplication.class, args);

        BookRepository bookRepository = context.getBean(BookRepository.class);
        bookRepository.findAll().forEach(System.out::println);

    }

}
