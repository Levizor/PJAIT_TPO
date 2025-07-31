package pl.edu.pja.tpo09.Models;

public class BMIResponse {
    float weight;
    float height;
    int bmi;
    String type;

    public BMIResponse() {
    }

    public BMIResponse(int weight, int height, int bmi, String type) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.type = type;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
