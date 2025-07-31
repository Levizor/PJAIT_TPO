package pja.edu.pl.tpo11.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pja.edu.pl.tpo11.DTOs.LinkRequestBodyDTO;
import pja.edu.pl.tpo11.DTOs.LinkResponseDTO;
import pja.edu.pl.tpo11.Exceptions.NotFoundExcepiton;
import pja.edu.pl.tpo11.Exceptions.WrongPasswordException;
import pja.edu.pl.tpo11.Services.LinkService;
import pja.edu.pl.tpo11.Services.URLService;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        path = "/api/links"
)
public class ApiController {

    private LinkService linkService;
    private URLService urlService;

    public ApiController(LinkService linkService, URLService urlService) {
        this.linkService = linkService;
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<LinkResponseDTO> createLink(@RequestBody @Valid LinkRequestBodyDTO linkDTO, HttpServletRequest req) {
        LinkResponseDTO linkResponseDTO = linkService.createLink(linkDTO);
        return ResponseEntity.created(URI.create(urlService.buildApiLinkUrl(linkResponseDTO.getId()))).body(linkResponseDTO);
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
    public ResponseEntity updateLink(@PathVariable String id, @RequestBody @Valid LinkRequestBodyDTO updateLinkDTO) {
        try {
            linkService.updateLinkApi(id, updateLinkDTO);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors
                    .computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });

        return errors;
    }
}
