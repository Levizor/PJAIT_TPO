package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo4blog.entities.Role;
import pl.edu.pja.tpo4blog.entities.Table;
import pl.edu.pja.tpo4blog.entities.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddUser extends AddEntity {

    public AddUser(Scanner scanner, DBService dbService, PromptPrinter prompt) {
        super(scanner, dbService, prompt);
    }

    @Override
    public void add() {
        User user = new User();

        System.out.println("Enter user email:");
        prompt.print();
        String email = scanner.nextLine().trim();
        if (checkQuit(email)) {
            return;
        }
        user.setEmail(email);

        List<Role> roles = (List<Role>) dbService.findAll(Table.ROLES);

        List<Role> roleList = new ArrayList<>();
        System.out.println("Choose roles for the user:");
        for (int i = 0; i < roles.size(); i++) {
            System.out.printf("%d. %s\n", i, roles.get(i).getName());
        }
        while (true) {
            prompt.print();
            String answer = scanner.nextLine().trim();
            if (checkQuit(answer)) {
                return;
            }

            try {
                int[] ints = readInts(answer);
                roleList.clear();

                boolean valid = true;
                for (int i = 0; i < ints.length; i++) {
                    if (ints[i] < 0 || ints[i] >= roles.size()) {
                        System.out.println("Invalid role number. Please try again.");
                        valid = false;
                        break;
                    }
                    roleList.add(roles.get(ints[i]));
                }

                if (valid) break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
                continue;
            }

        }

        user.setRoles(new HashSet<>(roleList));

        try{
            dbService.saveUser(user);
            System.out.println("User added.");
        } catch (Exception e){
            System.out.println("User could not be added.");
            System.out.println(e.getMessage());
        }

    }
}
