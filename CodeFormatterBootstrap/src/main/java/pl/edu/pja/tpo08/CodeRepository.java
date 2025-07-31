package pl.edu.pja.tpo08;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CodeRepository {
    private static final Map<String, Code> codes = new ConcurrentHashMap<>();
    private final String serializedCodesFileName;

    public CodeRepository(@Value("${custom.serialization.filename}") String serializedCodesFileName) {
        this.serializedCodesFileName = serializedCodesFileName;
    }

    public Optional<Code> findById(String id) {
        return Optional.ofNullable(codes.get(id));
    }

    public boolean save(Code code) {
        if (codes.containsKey(code.getId().trim())) {
            return false;
        }
        codes.put(code.getId().trim(), code);
        return true;
    }

    public Map<String, Code> getCodes() {
        return codes;
    }

    public void serialize() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serializedCodesFileName))) {
            out.writeObject(codes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        File file = new File(serializedCodesFileName);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                Map<String, Code> loadedCodes = (Map<String, Code>) in.readObject();
                codes.putAll(loadedCodes);
                System.out.println("Loaded " + loadedCodes.size() + " codes");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
