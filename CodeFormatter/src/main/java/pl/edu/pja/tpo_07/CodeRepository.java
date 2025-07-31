package pl.edu.pja.tpo_07;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CodeRepository {
    private static final Map<String, Code> codes = new HashMap<>();

    public CodeRepository() {}

    public Optional<Code> findById(String id) {
        return Optional.ofNullable(codes.get(id));
    }

    public boolean save(Code code) {
        if (codes.containsKey(code.getId())) {
            return false;
        }
        codes.put(code.getId(), code);
        return true;
    }
}
