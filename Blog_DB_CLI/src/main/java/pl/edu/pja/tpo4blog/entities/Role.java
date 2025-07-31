package pl.edu.pja.tpo4blog.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@jakarta.persistence.Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
