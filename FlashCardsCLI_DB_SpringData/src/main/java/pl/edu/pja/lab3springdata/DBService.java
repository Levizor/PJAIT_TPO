package pl.edu.pja.lab3springdata;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.pja.lab3springdata.enums.Language;
import pl.edu.pja.lab3springdata.enums.Order;

import java.util.List;
import java.util.stream.Stream;

@Service
public class DBService {
    EntryRepository entryRepository;

    public DBService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }
    public void addEntry(Entry entry) {
        entry.setId(entryRepository.count()+1);
        entryRepository.save(entry);
    }

    public Entry getRandomEntry() {
        var entries = getAllEntries();
        int index = (int) (Math.random() * entries.size());
        return entries.get(index);
    }

    public List<Entry> getAllEntries() {
        return entryRepository.findAllBy();
    }
    public Stream<Entry> getAllEntriesStream() {
        return getAllEntries().stream();
    }

    public List<Entry> findEntryByWord(String word) {
        return entryRepository.findByEnglishContainingIgnoreCaseOrPolishContainingIgnoreCaseOrGermanContainingIgnoreCase(word, word, word);
    }

    public List<Entry> getAllEntriesSortedBy(Language language, Order order){
        String languageFileld = language.name().toLowerCase();
        Sort.Direction direction = (order == Order.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC;

        return entryRepository.findAll(Sort.by(direction, languageFileld));
    }

    public void updateEntry(Entry entry){
        entryRepository.save(entry);
    }

    public void deleteEntry(Entry entry){
        entryRepository.delete(entry);
    }

}
