package pja.edu.pl.tpo11.DTOs;

import pja.edu.pl.tpo11.Models.RedirectLink;

public class LinkResponseDTO {
    String id;
    String name;
    String targetUrl;
    String redirectUrl;
    int visits;

    public static LinkResponseDTO fromLink(RedirectLink redirectLink){
        LinkResponseDTO responseDTO = new LinkResponseDTO();
        responseDTO.setId(redirectLink.getId());
        responseDTO.setName(redirectLink.getName());
        responseDTO.setVisits(redirectLink.getVisits());
        responseDTO.setTargetUrl(redirectLink.getTargetUrl());
        return responseDTO;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }
}
