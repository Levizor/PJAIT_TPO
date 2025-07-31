package pja.edu.pl.tpo10.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pja.edu.pl.tpo10.Models.RedirectLink;

@Repository
public interface RedirectLinkRepository extends CrudRepository<RedirectLink, String> {
}
