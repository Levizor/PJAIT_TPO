package pja.edu.pl.tpo10.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.loader.ast.internal.SingleUniqueKeyEntityLoaderStandard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pja.edu.pl.tpo10.DTOs.LinkRequestBodyDTO;
import pja.edu.pl.tpo10.DTOs.LinkResponseDTO;
import pja.edu.pl.tpo10.Exceptions.NotFoundExcepiton;
import pja.edu.pl.tpo10.Exceptions.WrongPasswordException;
import pja.edu.pl.tpo10.Models.RedirectLink;
import pja.edu.pl.tpo10.Services.LinkService;
import pja.edu.pl.tpo10.Services.URLService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

@RestController
@RequestMapping(
        path = "/api/links"
)
public class LinkController {

    private LinkService linkService;
    private URLService urlService;

    public LinkController(LinkService linkService, URLService urlService) {
        this.linkService = linkService;
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<LinkResponseDTO> createLink(@RequestBody LinkRequestBodyDTO linkDTO) {
        RedirectLink link = linkService.createLink(linkDTO);
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        linkResponseDTO.setName(link.getName());
        linkResponseDTO.setId(link.getId());
        linkResponseDTO.setVisits(link.getVisits());
        linkResponseDTO.setTargetUrl(link.getTargetUrl());
        linkResponseDTO.setRedirectUrl(urlService.buildRedirectUrl(link.getId()));

        return ResponseEntity.created(URI.create(urlService.buildApiLinkUrl(link.getId()))).body(linkResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkResponseDTO> getLinkInfo(@PathVariable String id) {
        return linkService.getLink(id).map(
                link -> {
                    LinkResponseDTO responseDTO = new LinkResponseDTO();
                    responseDTO.setId(link.getId());
                    responseDTO.setName(link.getName());
                    responseDTO.setTargetUrl(link.getTargetUrl());
                    responseDTO.setVisits(link.getVisits());
                    responseDTO.setRedirectUrl(urlService.buildRedirectUrl(link.getId()));

                    return ResponseEntity.ok(responseDTO);
                }
        ).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateLink(@PathVariable String id, @RequestBody LinkRequestBodyDTO updateLinkDTO) {
        try {
            linkService.updateLink(id, updateLinkDTO);
            return ResponseEntity.noContent().build();
        } catch (NotFoundExcepiton e) {
            return ResponseEntity.notFound().build();
        } catch (WrongPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("reason", "wrong password").build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLink(@PathVariable String id, @RequestHeader(value = "pass", required = false) String password) {
        try {
            linkService.deleteLink(id, password);
            return ResponseEntity.noContent().build();
        } catch (WrongPasswordException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("reason", "wrong password").build();
        }
    }
}
