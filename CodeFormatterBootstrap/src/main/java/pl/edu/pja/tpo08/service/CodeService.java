package pl.edu.pja.tpo08.service;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo08.Code;
import pl.edu.pja.tpo08.CodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CodeService {
    private final CodeRepository repository;

    public CodeService(CodeRepository repository) {
        this.repository = repository;
    }

    public Optional<Code> findById(String id) {
        return repository.findById(id);
    }

    public boolean save(Code code) {
        code.setTimestamp(LocalDateTime.now());
        boolean result =  repository.save(code);
        repository.serialize();

        return result;
    }

    public void removeExpired() {
        var codes = repository.getCodes();

        for (var k : codes.keySet()) {
            if (codes.get(k).isExpired()) {
                codes.remove(k);
            }
        }
    }

    public void serialize() {
        repository.serialize();
    }

    public void deserialize() {
        repository.deserialize();
    }
}
