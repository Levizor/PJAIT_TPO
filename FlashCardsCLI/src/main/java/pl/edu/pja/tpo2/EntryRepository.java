package pl.edu.pja.tpo2;

import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Repository
public class EntryRepository {
    private List<Entry> entries = new ArrayList<>();

    public EntryRepository() {
    }

    public void addEntry(Entry entry){
        entries.add(entry);
    }

    public List<Entry> getAllEntries(){
        return entries;
    }

    public Stream<Entry> getAllEntriesStream(){
        return entries.stream();
    }

    public Entry getRandomEntry(){
        int index = ThreadLocalRandom.current().nextInt(entries.size());
        return entries.get(index);
    }

}
