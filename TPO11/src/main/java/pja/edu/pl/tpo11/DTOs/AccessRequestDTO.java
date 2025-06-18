package pja.edu.pl.tpo11.DTOs;

import jakarta.validation.constraints.Size;
import pja.edu.pl.tpo11.ValidationConstraints.ValidPasswordOrBlank;

public class AccessRequestDTO {
    public String id;
    @Size(min=5, max=20)
    public String name;
    @ValidPasswordOrBlank
    public String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
