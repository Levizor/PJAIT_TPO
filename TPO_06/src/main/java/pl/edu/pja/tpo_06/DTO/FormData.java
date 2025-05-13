package pl.edu.pja.tpo_06.DTO;

import java.util.List;

public class FormData {
    private int numOfEntries;
    private String language;
    private List<String> additionalAttributes;

    public FormData() {}

    public FormData(int numOfEntries, String language, List<String> additionalAttributes) {
        this.numOfEntries = numOfEntries;
        this.language = language;
        this.additionalAttributes = additionalAttributes;
    }

    public int getNumOfEntries() {
        return numOfEntries;
    }

    public void setNumOfEntries(int numOfEntries) {
        this.numOfEntries = numOfEntries;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setAdditionalAttributes(List<String> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
