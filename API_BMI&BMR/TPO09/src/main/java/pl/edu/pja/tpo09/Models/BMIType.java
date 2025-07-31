package pl.edu.pja.tpo09.Models;

public enum BMIType {
    Underweight, Normal, Overweight, Obese;

    public static BMIType fromBMI(double BMI){
        if (BMI < 18.5){
            return Underweight;
        }else if (BMI < 25){
            return Normal;
        }else if (BMI < 30){
            return Overweight;
        }else {
            return Obese;
        }
    }
}
