package pja.edu.pl.tpo11.DTOs;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import pja.edu.pl.tpo11.Models.RedirectLink;

public class LinkUpdateRequest {
    String id;
    @Size(min = 5, max = 20)
    String name;

    @URL(protocol = "https", message = "{url.https}")
    String targetUrl;

    int visits;

    public static LinkUpdateRequest fromLink(RedirectLink link) {
        LinkUpdateRequest dto = new LinkUpdateRequest();
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetUrl());
        dto.setId(link.getId());
        dto.setVisits(link.getVisits());
        return dto;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

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
}
