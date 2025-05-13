package pl.edu.pja.tpo4blog.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pja.tpo4blog.entities.Role;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public List<Role> findAllByNameContainingIgnoreCase(String query);
}
