package pl.edu.pja.lab3springdata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Entry {
    @Id
    private Long id;
    public String english;
    public String polish;
    public String german;

    public Entry(){

    }

    public Entry(Long id, String english, String polish, String german) {
        this.id = id;
        this.english = english;
        this.polish = polish;
        this.german = german;
    }


    public Entry(String english, String polish, String german) {
        this.english = english;
        this.polish = polish;
        this.german = german;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean updateFromCSV(String line) {
        String[] words = line.split("[\\s,;]+");
        if(words.length != 3) {
            // if failing to parse entry
            return false;
        }
        english = words[0];
        polish = words[1];
        german = words[2];
        return true;
    }

    public void updateFromEntry(Entry entry){
        this.english = entry.english;
        this.polish = entry.polish;
        this.german = entry.german;
    }


    public String toString() {
        return "%s, %s, %s".formatted(english, polish, german);
    }

    public String toCSV() {
        return "%s,%s,%s".formatted(english, polish, german);
    }

    public static Entry fromCSV(String line) {
        String[] words = line.split("[\\s,;]+");
        if(words.length != 3) {
            // if failing to parse entry
            return null;
        }
        return new Entry(words[0], words[1], words[2]);
    }

    public String[] asArray(){
        String[] array = new String[3];
        array[0] = english;
        array[1] = polish;
        array[2] = german;
        return array;
    }
}
