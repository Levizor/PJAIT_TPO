package pl.edu.pja.tpo4books;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();

    public Publisher() {

    }

    @Override
    public String toString() {
        return String.format("Publisher{name='%s'}", name);
    }
}
