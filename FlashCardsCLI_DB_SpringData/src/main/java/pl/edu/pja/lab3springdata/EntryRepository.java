package pl.edu.pja.lab3springdata;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Long> {
    List<Entry> findAllBy();
    List<Entry> findByEnglishContainingIgnoreCaseOrPolishContainingIgnoreCaseOrGermanContainingIgnoreCase(String english, String polish, String german);
    List<Entry> findAll(Sort sort);
}
