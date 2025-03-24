package pl.edu.pja.lab3entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pja.lab3entitymanager.enums.Language;
import pl.edu.pja.lab3entitymanager.enums.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Repository
public class EntryRepository {
    private final EntityManager entityManager;

    public EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Long getNewEntryId() {
        Long maxId = entityManager.createQuery("SELECT MAX(e.id) FROM Entry e", Long.class).getSingleResult();
        if(maxId == null){
            return 1L;
        }else {
            return maxId + 1;
        }
    }

    @Transactional
    public void addEntry(Entry entry){
        entry.setId(getNewEntryId());
        entityManager.persist(entry);
    }

    public List<Entry> getAllEntries(){
        return entityManager.createQuery("SELECT e FROM Entry e", Entry.class).getResultList();
    }


    public List<Entry> getAllEntriesSortedBy(Language language, Order order) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Entry> cq = cb.createQuery(Entry.class);
        Root<Entry> root = cq.from(Entry.class);

        String languageField = language.name().toLowerCase();

        if (order == Order.ASC) {
            cq.orderBy(cb.asc(root.get(languageField)));
        } else {
            cq.orderBy(cb.desc(root.get(languageField)));
        }

        TypedQuery<Entry> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    public Entry getRandomEntry(){
        List<Entry> entries = getAllEntries();
        int index = ThreadLocalRandom.current().nextInt(entries.size());
        return entries.get(index);
    }

    @Transactional
    public void removeEntry(Entry entry){
        findById(entry.getId()).ifPresent(entityManager::remove);
    }

    @Transactional
    public void updateEntry(Entry entry){
        findById(entry.getId()).ifPresent(e -> {
            e.updateFromEntry(entry);
            entityManager.merge(e);
        });
    }

    public Optional<Entry> findById(Long id){
        return Optional.ofNullable(entityManager.find(Entry.class, id));
    }

    public List<Entry> findEntryByWord(String word) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e FROM Entry e WHERE ");

        boolean first = true;
        for (Language lang : Language.values()) {
            if (!first) {
                queryBuilder.append(" OR ");
            }
            queryBuilder.append("e.").append(lang.name().toLowerCase()).append(" ILIKE :word");
            first = false;
        }

        String queryStr = queryBuilder.toString();

        TypedQuery<Entry> query = entityManager.createQuery(queryStr, Entry.class);
        query.setParameter("word", word);

        return query.getResultList();
    }
}
