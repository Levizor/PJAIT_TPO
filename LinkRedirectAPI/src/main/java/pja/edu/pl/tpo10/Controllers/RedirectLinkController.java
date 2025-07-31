package pja.edu.pl.tpo10.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pja.edu.pl.tpo10.Services.LinkService;

import java.net.URI;

@Controller
@RequestMapping(
        path = "/red"
)
public class RedirectLinkController {
    
    private LinkService linkService;
    public RedirectLinkController(LinkService linkService){
        this.linkService = linkService;
    }
    @GetMapping("/{id}")
    public ResponseEntity redirectToTargetLink(@PathVariable String id){
        return linkService.visitLink(id).map(
                link -> 
                    ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getTargetUrl())).build()
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
