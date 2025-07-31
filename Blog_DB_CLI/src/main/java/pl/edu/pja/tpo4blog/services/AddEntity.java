package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public abstract class AddEntity {
    protected boolean quit = false;
    Scanner scanner;
    DBService dbService;
    PromptPrinter prompt;

    public AddEntity(Scanner scanner, DBService dbService, PromptPrinter prompt) {
        this.scanner = scanner;
        this.dbService = dbService;
        this.prompt = prompt;
    }

    abstract public void add();

    public boolean quits(){
        var temp = quit;
        quit = false;
        return temp;
    }

    protected boolean checkQuit(String s){
        if(s.equalsIgnoreCase("quit")){
            quit = true;
        }
        return quit;
    }

    protected static int[] readInts(String line) throws NumberFormatException {
        String[] tokens = line.split("[,\\s+]");
        int[] ints = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            ints[i] = Integer.parseInt(tokens[i]);
        }
        return ints;
    }
}
