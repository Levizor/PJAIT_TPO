package pl.edu.pja.tpo4books;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {

    }

    @Override
    public String toString() {
        return String.format("Author{firstName='%s', lastName='%s'}", firstName, lastName);
    }
}
