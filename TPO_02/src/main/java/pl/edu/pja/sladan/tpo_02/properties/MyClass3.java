package pl.edu.pja.sladan.tpo_02.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "external.values")
public class MyClass3 {

    private int a;
    private int b;

    public MyClass3(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + " " + b;
    }
}
