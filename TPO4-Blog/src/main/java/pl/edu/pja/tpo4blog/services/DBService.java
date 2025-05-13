package pl.edu.pja.tpo4blog.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pja.tpo4blog.entities.*;
import pl.edu.pja.tpo4blog.repositories.*;

import java.util.List;

@Service
public class DBService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ArticleRepository articleRepository;
    private BlogRepository blogRepository;

    public DBService(UserRepository userRepository, RoleRepository roleRepository, ArticleRepository articleRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.articleRepository = articleRepository;
        this.blogRepository = blogRepository;
    }

    private CrudRepository<?, Long> getRepository(Table table) {
        return switch (table) {
            case BLOGS -> blogRepository;
            case ARTICLES -> articleRepository;
            case USERS -> userRepository;
            case ROLES -> roleRepository;
        };
    }

    public List<?> findAll(Table table) {
        return (List<?>) getRepository(table).findAll();
    }

    public List<?> search(Table table, String query) {
        return switch (table) {
            case BLOGS -> blogRepository.findAllByNameContainingIgnoreCase(query);
            case ARTICLES -> articleRepository.findAllByTitleContainingIgnoreCase(query);
            case USERS -> userRepository.findAllByEmailContainingIgnoreCase(query);
            case ROLES -> roleRepository.findAllByNameContainingIgnoreCase(query);
        };
    }

    @Modifying
    @Transactional
    public void delete(Table table, List<?> values) {
        var ids = (switch (table) {
            case ARTICLES -> ((List<Article>) values).stream().map(Article::getId);
            case USERS -> ((List<User>) values).stream().map(User::getId);
            case BLOGS -> ((List<Blog>) values).stream().map(Blog::getId);
            case ROLES -> ((List<Role>) values).stream().map(Role::getId);
        }).toList();

        var repository = getRepository(table);
        for (var id: ids){
            System.out.println(id);
            repository.deleteById(id);
        }
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    public void saveArticle(Article article){
        articleRepository.save(article);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveBlog(Blog blog){
        blogRepository.save(blog);
    }
}
