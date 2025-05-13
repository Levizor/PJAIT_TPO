package pl.edu.pja.tpo4blog.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pja.tpo4blog.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findAllByEmailContainingIgnoreCase(String email);
}
