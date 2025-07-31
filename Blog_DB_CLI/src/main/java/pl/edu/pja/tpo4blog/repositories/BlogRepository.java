package pl.edu.pja.tpo4blog.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pja.tpo4blog.entities.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {
    List<Blog> findAllByNameContainingIgnoreCase(String name);
}
