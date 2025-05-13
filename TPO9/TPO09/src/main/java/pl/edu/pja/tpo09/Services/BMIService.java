package pl.edu.pja.tpo09.Services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo09.Models.InvalidDataException;

@Service
public class BMIService {

    public double BMI(float weight, float height) throws InvalidDataException {
        if (weight <= 0 || height <= 0) {
            throw new InvalidDataException("invalid data, weight and height parameters must be positive numbers");
        }

        return weight / (Math.pow(height/100, 2));
    }
}
