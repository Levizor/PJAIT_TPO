package pl.edu.pja.lab3entitymanager;

import org.springframework.stereotype.Service;
import pl.edu.pja.lab3entitymanager.enums.Language;
import pl.edu.pja.lab3entitymanager.enums.Order;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class DBService {
    EntryRepository entryRepository;

    public DBService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }
    public void addEntry(Entry entry) {
        entryRepository.addEntry(entry);
    }

    public Entry getRandomEntry() {
        return entryRepository.getRandomEntry();
    }

    public List<Entry> getAllEntries() {
        return entryRepository.getAllEntries();
    }
    public Stream<Entry> getAllEntriesStream() {
        return getAllEntries().stream();
    }

    public List<Entry> findEntryByWord(String word) {
        return entryRepository.findEntryByWord(word);
    }
    public List<Entry> getAllEntriesSortedBy(Language language, Order order){
        return entryRepository.getAllEntriesSortedBy(language, order);
    }

    public void updateEntry(Entry entry){
        entryRepository.updateEntry(entry);
    }

    public void deleteEntry(Entry entry){
        entryRepository.removeEntry(entry);
    }

}
