package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo4blog.entities.Role;

import java.util.Scanner;

@Service
public class AddRole extends AddEntity {

    public AddRole(Scanner scanner, DBService dbService, PromptPrinter prompt) {
        super(scanner, dbService, prompt);
    }

    @Override
    public void add(){
        System.out.println("Enter role name: ");
        String roleName = scanner.nextLine().trim().toUpperCase();
        if(checkQuit(roleName)){
            return;
        }
        Role role = new Role(roleName);
        try{
            dbService.saveRole(role);
            System.out.println("Role added.");
        } catch (Exception e){
            System.out.println("Role could not be added.");
            System.out.println(e.getMessage());
        }
    }
}
