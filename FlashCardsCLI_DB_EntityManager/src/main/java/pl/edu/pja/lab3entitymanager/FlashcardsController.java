package pl.edu.pja.lab3entitymanager;

import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import pl.edu.pja.lab3entitymanager.DisplayServices.DisplayService;
import pl.edu.pja.lab3entitymanager.enums.Language;
import pl.edu.pja.lab3entitymanager.enums.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@org.springframework.core.annotation.Order(2)
public class FlashcardsController implements CommandLineRunner {
    private DBService dbService;
    private DisplayService displayService;
    private Mode mode;
    private Scanner scanner;

    @Value("classpath:help.txt")
    Resource help;

    public FlashcardsController(DBService dbService, DisplayService displayService, Scanner scanner) {
        this.dbService = dbService;
        this.displayService = displayService;
        this.scanner = scanner;
    }

    private void printHelp() throws IOException {
        displayService.display("Usage:");
        displayService.display(getHelpText());
    }

    private String getHelpText() throws IOException {
        return new String(help.getInputStream().readAllBytes());
    }

    private void printPrompt() {
        displayService.display("\n%s:".formatted(mode.toString()));
    }

    @Override
    public void run(String[] args) throws Exception {
        printHelp();
        while (true) {
            mode = Mode.Choice;
            printPrompt();
            String answer = scanner.nextLine().toLowerCase().trim();
            String[] arguments = answer.split("[\\s,;]+");
            String command = answer;
            if(arguments.length > 1) {
                command = arguments[0];
            }

            try {
                switch (command) {
                    case "help" -> printHelp();
                    case "quit" -> exit();
                    case "add" -> addWordMode();
                    case "test" -> testMode();
                    case "display" -> display(arguments);
                    case "search" -> search(arguments[1]);
                    case "edit" -> edit();
                }
            } catch (Exception e) {
                displayService.display(e.getMessage());
            }
        }

    }

    private List<Entry> search(String search) throws Exception {
        displayService.display("Searching for: %s".formatted(search));
        var entries = dbService.findEntryByWord(search);
        if(entries.isEmpty()){
            displayService.display("No entries found for search");
        }else {
            displayService.display("Found:");
            if(entries.size()>1){
                for (int i = 0; i < entries.size(); i++) {
                    displayService.display("[%d] %s".formatted(i+1, entries.get(i).toString()));
                }
            }
            else {
                displayService.display(entries.getFirst().toString());
            }
        }

        return entries;
    }

    private void editEntry(Entry entry){
        displayService.display("Editing %s".formatted(entry));
        displayService.display("Insert new entry or write \"DELETE\" to delete entry: ");
        printPrompt();
        String answer = scanner.nextLine().toLowerCase().trim();

        if(answer.equals("delete")) {
            dbService.deleteEntry(entry);
            displayService.display("Entry was deleted.");
            return;
        }

        if(entry.updateFromCSV(answer)){
            dbService.updateEntry(entry);
            displayService.display("Entry updated successfully");
        }else {
            displayService.display("Entry not updated. Try again or quit.");
        }
    }

    private void edit() throws Exception {
        mode = Mode.Edit;

        while(mode == Mode.Edit){
            mode = Mode.Search;
            displayService.display("Search for a word to edit first: ");
            printPrompt();
            String answer = scanner.nextLine().toLowerCase().trim();
            if (answer.equals("quit")){
                break;
            }
            mode = Mode.Edit;
            List<Entry> entries = search(answer);
            if(entries.isEmpty()){
                continue;
            }
            else if(entries.size()==1){
                Entry entry = entries.getFirst();
                editEntry(entry);
            }
            else {
                displayService.display("Select entry to edit:");
                printPrompt();
                answer = scanner.nextLine().toLowerCase().trim();
                int index = Integer.parseInt(answer) - 1;
                Entry entry = entries.get(index);
                if(entry == null){
                    displayService.display("Wrong index");
                }else {
                    editEntry(entry);
                }
            }
        }
    }

    private void display(String[] args) {
        if(args.length == 1){
            dbService.getAllEntriesStream().forEach(entry -> displayService.display(entry.toString()));
            return;
        }
        Language language = Language.valueOf(args[1].toUpperCase());
        Order order = Order.ASC;
        if (args.length > 2) {
            order = Order.valueOf(args[2].toUpperCase());
        }
        dbService.getAllEntriesSortedBy(language, order).forEach(entry -> displayService.display(entry.toString()));
    }

    public void exit() {
        scanner.close();
        System.exit(0);
    }

    private void testMode() {
        mode = Mode.Test;
        displayService.display("Entered test mode. To quit enter quit");
        displayService.display("You'll be given a word in one language, you are supposed to enter their" + "translations in two other languages.");

        String[] languages = {"English", "Polish", "German"};

        while (true) {
            Entry entry = dbService.getRandomEntry();
            String[] words = entry.asArray(); // english, polish, german

            int promptIndex = (int) (Math.random() * words.length);
            int[] answerIndecies = new int[2];

            answerIndecies[0] = (promptIndex + 1) % languages.length;
            answerIndecies[1] = (promptIndex + 2) % languages.length;

            displayService.display(
                    "Word in %s is \n%s\nEnter translations in %s and %s\n".formatted(
                    languages[promptIndex],
                    words[promptIndex],
                    languages[answerIndecies[0]],
                    languages[answerIndecies[1]]
                    )
            );
            printPrompt();

            String line = scanner.nextLine().toLowerCase().trim();
            if(line.equals("quit")) {
                break;
            }
            if(line.isBlank()){
                continue;
            }

            String[] answers = line.split("[\\s,;.]+");
            if(answers.length == 0) {
                displayService.display("No answer provided.");
            }

            for (int i = 0; i <Math.min(answers.length, 2); i++){
                if(answers[i].equalsIgnoreCase(words[answerIndecies[i]])) {
                    displayService.display("[%d] Correct".formatted(i));
                } else {
                    displayService.display("[%d] Incorrect".formatted(i));
                }
                System.out.print(" ");
            }
            displayService.display("");
            displayService.display("Correct answers:");
            displayService.display("%s %s\n\n".formatted(words[answerIndecies[0]], words[answerIndecies[1]]));
        }

        displayService.display("Exiting test mode.");
    }

    private void addWordMode() {
        mode = Mode.Add;
        displayService.display("Entered adding words mode. To quit enter quit");
        displayService.display("Enter words in form of: <English>, <Polish>, <German>");
        while (true) {
            printPrompt();

            String line = scanner.nextLine();

            if (line.trim().equalsIgnoreCase("quit")) {
                break;
            }

            Entry entry = Entry.fromCSV(line);
            if (entry != null) {
                dbService.addEntry(entry);
                displayService.display("Entry added");
            } else {
                displayService.display("Failed to parse entry");
            }
        }

        displayService.display("Quiting adding words mode.");
    }

}
