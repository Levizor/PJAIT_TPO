package pja.edu.pl.tpo11.DTOs;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import pja.edu.pl.tpo11.ValidationConstraints.UniqueTargetUrl;
import pja.edu.pl.tpo11.ValidationConstraints.ValidPasswordOrBlank;

public class LinkRequestBodyDTO {
    @Size(min=5, max=20)
    String name;

    @URL(protocol = "https", message = "{url.https}")
    @UniqueTargetUrl
    String targetUrl;

    @ValidPasswordOrBlank
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
