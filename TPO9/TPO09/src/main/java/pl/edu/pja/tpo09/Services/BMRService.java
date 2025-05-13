package pl.edu.pja.tpo09.Services;

import org.springframework.stereotype.Service;
import pl.edu.pja.tpo09.Models.InvalidDataException;
import pl.edu.pja.tpo09.Models.InvalidGenderDataException;

@Service
public class BMRService {

    public double BMR(String gender, int age, float weight, float height) {
        if (weight <= 0 || height <= 0 || age <= 0) {
            throw new InvalidDataException("invalid data, weight, height, and age parameters must be positive numbers");
        }

        if (!gender.equalsIgnoreCase("man") && !gender.equalsIgnoreCase("woman")) {
            throw new InvalidGenderDataException("invalid gender data");
        }

        if (gender.equalsIgnoreCase("man")) {
            return 13.397 * weight + 4.799 * height - 5.677 * age + 88.362;
        } else {
            return 9.247 * weight + 3.098 * height - 4.330 * age + 447.593;
        }
    }
}
