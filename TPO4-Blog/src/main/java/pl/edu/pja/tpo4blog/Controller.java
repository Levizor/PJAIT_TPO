package pl.edu.pja.tpo4blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pja.tpo4blog.entities.Table;
import pl.edu.pja.tpo4blog.services.*;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller implements CommandLineRunner {
    private final DBService dbService;
    Map<Table, AddEntity> addEntities = new HashMap<>();

    private PromptPrinter prompt;


    private Table currentTable = Table.ARTICLES;
    private final Scanner scanner;


    public Controller(DBService dbService, Scanner scanner, PromptPrinter prompt, AddRole addRole, AddUser addUser, AddBlog addBlog, AddArticle addArticle) {
        this.dbService = dbService;
        this.scanner = scanner;
        this.addEntities = Map.of(
                Table.ROLES, addRole,
                Table.ARTICLES, addArticle,
                Table.BLOGS, addBlog,
                Table.USERS, addUser
        );
        this.prompt = prompt;
    }

    private void printHelp(){
        System.out.println("\nUsage:");
        System.out.println("ct <table> - change current table");
        System.out.println("view - view current table data");
        System.out.println("search <term> - search current table data");
        System.out.println("add - add data to table");
        System.out.println("delete - delete current table data");
        System.out.println("quit - to go back");
        System.out.println();
    }

    private void printPrompt(){
        prompt.print();
    }


    private void ct(String... args){
        if(args.length < 2){
            throw new IllegalArgumentException("You need to specify at least two arguments");
        }

        String tableName = args[1].toUpperCase().trim();

         setCurrentTable(Table.valueOf(tableName));
    }

    private List<?> search(String... args){
        String searchTerm = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        var values = dbService.search(currentTable, searchTerm);
        for(int i = 0; i < values.size(); i++){
            System.out.printf("[%d] %s\n", i + 1, values.get(i));
        }
        return values;
    }

    protected void delete(String... _args){
        prompt.setCurrentMode(Mode.DELETE);
        System.out.println("Entering delete mode.");

        while(true){
            System.out.println("Searching. Enter search query or quit to quit:");
            prompt.setCurrentMode(Mode.SEARCH);
            printPrompt();
            String searchQuery = scanner.nextLine().trim();
            if(searchQuery.toLowerCase().equals("quit")){
                break;
            }
            var values = dbService.search(currentTable, searchQuery);
            if(values.isEmpty()) {
                System.out.println("No data found.");
                continue;
            }

            System.out.printf("%d values were found. Enter numbers to delete or enter to skip:\n", values.size());
            for(int i = 0; i < values.size(); i++){
                System.out.printf("[%d] %s\n", i + 1, values.get(i));
            }
            System.out.println();
            String numbersToDelete = scanner.nextLine().trim();
            if(numbersToDelete.isBlank()){
                continue;
            }

            int[] numbers = Arrays.stream(numbersToDelete.split("[,\\s+]")).mapToInt(Integer::parseInt).toArray();

            var selectedList = new ArrayList<>();

            for(int i = 0; i < numbers.length; i++){
                selectedList.add(values.get(i));
            }

            dbService.delete(currentTable, selectedList);
            System.out.println("Values deleted\n");
        }
    }

    protected void view(String... args){
        var values = dbService.findAll(currentTable);
        values.forEach(System.out::println);
    }

    private void add(String... args){
        prompt.setCurrentMode(Mode.ADD);
        System.out.println("Entering adding mode.");
        AddEntity addEntity = addEntities.get(currentTable);
        while(true){
            addEntity.add();
            if(addEntity.quits()){
                break;
            }
        }
    }

    public void setCurrentTable(Table currentTable) {
        this.currentTable = currentTable;
        prompt.setCurrentTable(currentTable);
    }

    @Override
    public void run(String... args) throws Exception {
        printHelp();

        while(true){
            prompt.setCurrentMode(Mode.NONE);
            printPrompt();
            String answer = scanner.nextLine().toLowerCase().trim();
            String[] arguments = answer.split("[\\s,;]+");
            String command = arguments[0];

            try {

                switch (command) {
                    case "help" -> printHelp();
                    case "ct" -> ct(arguments);
                    case "search" -> search(arguments);
                    case "delete" -> delete(arguments);
                    case "view" -> view(arguments);
                    case "add" -> add(arguments);
                    case "quit" -> System.exit(0);
                    default -> {}
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
