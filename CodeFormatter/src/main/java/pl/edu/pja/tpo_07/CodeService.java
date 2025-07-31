package pl.edu.pja.tpo_07;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CodeService {
    private CodeRepository repository;

    public CodeService(CodeRepository repository) {
        this.repository = repository;
    }

    public Optional<Code> findById(String id) {
        return repository.findById(id);
    }

    public boolean save(Code code) {
        return repository.save(code);
    }

}
