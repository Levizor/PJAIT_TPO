package pl.edu.pja.tpo2;

public class Entry {
    public String english;
    public String polish;
    public String german;

    public Entry(String english, String polish, String german) {
        this.english = english;
        this.polish = polish;
        this.german = german;
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
