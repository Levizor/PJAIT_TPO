package pja.edu.pl.tpo10.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class URLService {
    public String buildUrl(String path){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString();
    }
    
    public String buildApiLinkUrl(String linkId){
        return buildRedirectUrl("/api/links/%s".formatted(linkId));
    }
    
    public String buildRedirectUrl(String linkId){
        return buildUrl("/red/%s".formatted(linkId));
    }
}
