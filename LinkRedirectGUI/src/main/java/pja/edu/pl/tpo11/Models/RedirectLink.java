package pja.edu.pl.tpo11.Models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

@Entity
public class RedirectLink {
    @Id
    private String id;
    private String name;
    private String targetUrl;
    
    private String password;
    private int visits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
    
    public void incrementVisits(){
        this.visits++;
    }
}
