package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo4blog.entities.Blog;
import pl.edu.pja.tpo4blog.entities.Table;
import pl.edu.pja.tpo4blog.entities.User;

import java.util.List;
import java.util.Scanner;

@Service
public class AddBlog extends AddEntity {

    public AddBlog(Scanner scanner, DBService dbService, PromptPrinter prompt) {
        super(scanner, dbService, prompt);
    }

    @Override
    public void add() {
        Blog blog = new Blog();

        System.out.println("Enter blog name:");
        prompt.print();
        String blogName = scanner.nextLine().trim();
        if (checkQuit(blogName)) {
            return;
        }

        blog.setName(blogName);

        while (true){
            try {

                System.out.println("Search for blog manager by email:");
                prompt.print();
                String email = scanner.nextLine().trim();
                if (checkQuit(email)) {
                    return;
                }

                List<User> users = (List<User>) dbService.search(Table.USERS, email);
                if (users.isEmpty()) {
                    System.out.println("User not found.");
                    continue;
                }

                System.out.println("Users found:");
                for (int i = 0; i < users.size(); i++) {
                    System.out.printf("[%d] %s\n", i, users.get(i));
                }
                System.out.println("Which user to assign as a manager:");
                prompt.print();
                String answer = scanner.nextLine();
                if (checkQuit(answer)) {
                    return;
                }
                int number = Integer.parseInt(answer);

                if(number < 0 || number > users.size()) {
                    System.out.println("Invalid number. Please try again.");
                    continue;
                }

                blog.setManager(users.get(number));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            break;
        }

        try{
            dbService.saveBlog(blog);
            System.out.println("Blog added.");
        } catch (Exception e){
            System.out.println("Blog could not be added.");
            System.out.println(e.getMessage());
        }
    }

}
