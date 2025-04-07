package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public abstract class AddEntity {
    protected boolean quit = false;
    Scanner scanner;
    DBService dbService;

    public AddEntity(Scanner scanner, DBService dbService) {
        this.scanner = scanner;
        this.dbService = dbService;
    }

    abstract public void add();

    public boolean quits(){
        var temp = quit;
        quit = false;
        return temp;
    }
}
