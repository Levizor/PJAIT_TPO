package pl.edu.pja.tpo_06.DTO;

public class Headers extends Person {
    private String dateOfBirth;

    @Override
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
