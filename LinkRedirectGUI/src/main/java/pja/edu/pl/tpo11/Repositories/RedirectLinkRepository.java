package pja.edu.pl.tpo11.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pja.edu.pl.tpo11.Models.RedirectLink;

@Repository
public interface RedirectLinkRepository extends CrudRepository<RedirectLink, String> {
    boolean existsByNameEquals(String name);
    boolean existsByTargetUrlEquals(String url);
}
