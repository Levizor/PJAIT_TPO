package pl.edu.pja.sladan.tpo_02.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:new.properties")
public class MyClass2 {

    private int a;
    private int b;

    public MyClass2(@Value("${external.values.firstValue}") int a, @Value("${external.values.secondValue}") int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + " " + b;
    }
}
