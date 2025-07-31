package pl.edu.pja.tpo2;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import pl.edu.pja.tpo2.DisplayServices.DisplayService;

import java.io.IOException;
import java.util.Scanner;

@Controller
public class FlashcardsController implements CommandLineRunner {
    private FileService fileService;
    private DisplayService displayService;
    private Mode mode;
    private Scanner scanner;

    @Value("classpath:help.txt")
    Resource help;

    public FlashcardsController(FileService fileService, DisplayService displayService, Scanner scanner) {
        this.fileService = fileService;
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
        displayService.display("\n%s\n> ".formatted(mode.toString()));
    }

    @Override
    public void run(String[] args) throws Exception {
        fileService.loadFromCSV();
        printHelp();
        while (true) {
            mode = Mode.Choice;
            printPrompt();
            String answer = scanner.nextLine().toLowerCase().trim();

            switch (answer) {
                case "help" -> printHelp();
                case "quit" -> exit();
                case "add" -> addWordMode();
                case "test" -> testMode();
                case "display" -> display();
            }
        }

    }

    private void display() {
        fileService.getAllEntriesStream().forEach(entry -> displayService.display(entry.toString()));
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
            Entry entry = fileService.getRandomEntry();
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
                fileService.addEntry(entry);
                displayService.display("Entry added");
                fileService.saveToCSV();
            } else {
                displayService.display("Failed to parse entry");
            }
        }

        displayService.display("Quiting adding words mode.");
    }

}
