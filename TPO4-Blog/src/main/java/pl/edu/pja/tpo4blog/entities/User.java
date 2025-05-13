package pl.edu.pja.tpo4blog.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@jakarta.persistence.Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "manager", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Blog managedBlog;

    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Article> articles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(){}

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blog getManagedBlog() {
        return managedBlog;
    }

    public void setManagedBlog(Blog managedBlog) {
        this.managedBlog = managedBlog;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s", email);
    }
}