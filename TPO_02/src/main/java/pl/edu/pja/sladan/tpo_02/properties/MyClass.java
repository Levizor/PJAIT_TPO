package pl.edu.pja.sladan.tpo_02.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyClass {

    private int a;
    private int b;

    public MyClass(@Value("${MyApp.values.firstValue}") int a, @Value("${MyApp.values.secondValue}") int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + " " + b;
    }
}
