package pl.edu.pja.tpo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileService {

    EntryRepository entryRepository;
    String filename;

    public FileService(EntryRepository entryRepository, String filename) {
        this.entryRepository = entryRepository;
        this.filename = filename;
    }

    public void loadFromCSV() {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.map(Entry::fromCSV).filter(Objects::nonNull).forEach(entry -> entryRepository.addEntry(entry));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToCSV() {
        Path path = Paths.get(filename);

        List<String> lines = entryRepository.getAllEntriesStream().map(Entry::toCSV).toList();

        try {
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(Entry entry) {
        entryRepository.addEntry(entry);
    }

    public Entry getRandomEntry() {
        return entryRepository.getRandomEntry();
    }

    public Stream<Entry> getAllEntriesStream() {
        return entryRepository.getAllEntriesStream();
    }
}
