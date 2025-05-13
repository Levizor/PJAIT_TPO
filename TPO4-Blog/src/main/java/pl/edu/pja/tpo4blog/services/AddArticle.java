package pl.edu.pja.tpo4blog.services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo4blog.entities.Article;
import pl.edu.pja.tpo4blog.entities.Blog;
import pl.edu.pja.tpo4blog.entities.Table;
import pl.edu.pja.tpo4blog.entities.User;

import java.util.List;
import java.util.Scanner;

@Service
public class AddArticle extends AddEntity {

    public AddArticle(Scanner scanner, DBService dbService, PromptPrinter prompt) {
        super(scanner, dbService, prompt);
    }

    @Override
    public void add() {
        Article article = new Article();

        System.out.println("Enter title: ");
        String title = scanner.nextLine().trim();
        if (checkQuit(title)) {
            return;
        }
        article.setTitle(title);

        while (!addBlog(article)) {
            if (quit) return;
        }

        try{
            dbService.saveArticle(article);
            System.out.println("Article added.");
        } catch (Exception e){
            System.out.println("Article could not be added.");
            System.out.println(e.getMessage());
        }
    }

    private boolean addBlog(Article article) {
        System.out.println("Search for blog by name: ");
        String name = scanner.nextLine().trim();
        if (checkQuit(name)) {
            return false;
        }

        List<Blog> blogs = (List<Blog>) dbService.search(Table.BLOGS, name);
        if (blogs.isEmpty()) {
            System.out.println("Blog not found.");
            return false;
        }

        System.out.println("Blogs found:");
        for (int i = 0; i < blogs.size(); i++) {
            System.out.printf("[%d] %s\n", i, blogs.get(i));
        }
        System.out.println("Which blog to assign article to:");
        String answer = scanner.nextLine();
        if (checkQuit(answer)) {
            return false;
        }
        int number = Integer.parseInt(answer);

        if(number < 0 || number > blogs.size()) {
            System.out.println("Invalid number. Please try again.");
            return false;
        }

        Blog blog = blogs.get(number);
        article.setBlog(blog);
        article.setAuthor(blog.getManager());

        return true;
    }
}
