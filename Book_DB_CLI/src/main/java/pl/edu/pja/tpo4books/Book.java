package pl.edu.pja.tpo4books;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @ManyToOne
    private Publisher publisher;

    public Book() {

    }

    public Book(String title, List<Author> authors, Publisher publisher) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return String.format("Book{title='%s', authors=%s, publisher=%s}", title, authors, publisher);
    }
}
