package pl.edu.pja.tpo09.Models;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String s) {
        super(s);
    }
}
