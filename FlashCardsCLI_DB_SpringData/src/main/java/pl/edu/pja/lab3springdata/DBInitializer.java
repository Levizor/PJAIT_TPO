package pl.edu.pja.lab3springdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;

@Controller
@Order(1)
public class DBInitializer implements CommandLineRunner {
    private DBService dbService;

    public DBInitializer(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run(String... args) {
        if(dbService.getAllEntries().isEmpty()){
            dbService.addEntry(new Entry("Apple", "Jabłko", "Apfel"));
            dbService.addEntry(new Entry("House", "Dom", "Haus"));
            dbService.addEntry(new Entry("Car", "Samochód", "Auto"));
            dbService.addEntry(new Entry("Dog", "Pies", "Hund"));
            dbService.addEntry(new Entry("Water", "Woda", "Wasser"));
            dbService.addEntry(new Entry("Sun", "Słońce", "Sonne"));
            dbService.addEntry(new Entry("Book", "Książka", "Buch"));
            dbService.addEntry(new Entry("Chair", "Krzesło", "Stuhl"));
            dbService.addEntry(new Entry("School", "Szkoła", "Schule"));
            dbService.addEntry(new Entry("Food", "Jedzenie", "Essen"));
            dbService.addEntry(new Entry("Friend", "Przyjaciel", "Freund"));
            dbService.addEntry(new Entry("Computer", "Komputer", "Computer"));
            dbService.addEntry(new Entry("Family", "Rodzina", "Familie"));
        }
    }
}
