package pja.edu.pl.tpo11.Controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pja.edu.pl.tpo11.DTOs.AccessRequestDTO;
import pja.edu.pl.tpo11.DTOs.LinkRequestBodyDTO;
import pja.edu.pl.tpo11.DTOs.LinkResponseDTO;
import pja.edu.pl.tpo11.DTOs.LinkUpdateRequest;
import pja.edu.pl.tpo11.Exceptions.NotFoundExcepiton;
import pja.edu.pl.tpo11.Exceptions.WrongPasswordException;
import pja.edu.pl.tpo11.Models.RedirectLink;
import pja.edu.pl.tpo11.Services.LinkService;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ViewController {

    private LinkService linkService;

    public ViewController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/red/{id}")
    public ResponseEntity redirectToTargetLink(@PathVariable String id) {
        return linkService.visitLink(id).map(
                link ->
                        ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getTargetUrl())).build()
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/newLink");
    }

    @GetMapping("/newLink")
    public String index(Model model) {
        if (!model.containsAttribute("linkRequest")) {
            model.addAttribute("linkRequest", new LinkRequestBodyDTO());
        }

        return "newLink";
    }

    @PostMapping("/newLink")
    public String NewLink(@Valid @ModelAttribute("linkRequest") LinkRequestBodyDTO linkRequest,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "newLink";
        }
        LinkResponseDTO created = linkService.createLink(linkRequest);
        @SuppressWarnings("unchecked")
        Set<String> authorizedLinks = (Set<String>) session.getAttribute("authorizedLinks");
        if (authorizedLinks == null) {
            authorizedLinks = new HashSet<>();
        }
        authorizedLinks.add(created.getId());
        session.setAttribute("authorizedLinks", authorizedLinks);

        redirectAttributes.addFlashAttribute("link", created);
        return "redirect:/manage/" + created.getId();
    }

    @GetMapping("/manage/{id}")
    public String manageLink(@PathVariable String id, Model model, HttpSession session) {
        var linkOpt = linkService.getLink(id);

        if (linkOpt.isEmpty()) {
            model.addAttribute("error", "Link not found");
            return "errorPage";
        }

        var link = linkOpt.get();

        if (link.getPassword() != null && !link.getPassword().isEmpty()) {
            @SuppressWarnings("unchecked")
            Set<String> authorizedLinks = (Set<String>) session.getAttribute("authorizedLinks");
            if (authorizedLinks != null && authorizedLinks.contains(id)) {
                model.addAttribute("linkId", id);
                model.addAttribute("link", LinkUpdateRequest.fromLink(link));
                model.addAttribute("editable", true);
                return "manageLink";
            } else {
                model.addAttribute("accessRequest", new AccessRequestDTO());
                model.addAttribute("linkId", id);
                return "manageAccessForm";
            }
        } else {
            model.addAttribute("linkId", id);
            model.addAttribute("link", LinkUpdateRequest.fromLink(link));
            model.addAttribute("editable", false);
            return "manageLink";
        }
    }

    @PostMapping("/manage/{id}/access")
    public String verifyAccess(
            @PathVariable String id,
            @ModelAttribute AccessRequestDTO accessRequest,
            Model model,
            HttpSession session
    ) {
        var linkOpt = linkService.getLink(id);

        if (linkOpt.isEmpty()) {
            model.addAttribute("error", "Link not found");
            return "errorPage";
        }

        var link = linkOpt.get();

        if (link.getPassword() != null &&
                link.getPassword().equals(accessRequest.getPassword()) &&
                link.getName().equals(accessRequest.getName())) {

            @SuppressWarnings("unchecked")
            Set<String> authorizedLinks = (Set<String>) session.getAttribute("authorizedLinks");
            if (authorizedLinks == null) {
                authorizedLinks = new HashSet<>();
            }

            authorizedLinks.add(id);
            session.setAttribute("authorizedLinks", authorizedLinks);

            return "redirect:/manage/" + id;
        } else {
            model.addAttribute("accessRequest", accessRequest);
            model.addAttribute("linkId", id);
            model.addAttribute("error", "Invalid name or password");
            return "manageAccessForm";
        }
    }

    @PostMapping("/manage/{id}/update")
    public String updateLink(@PathVariable String id,
                             @Valid @ModelAttribute("link") LinkUpdateRequest linkUpdate,
                             BindingResult result,
                             Model model,
                             HttpSession session) {

        var linkOpt = linkService.getLink(id);
        if (linkOpt.isEmpty()) {
            model.addAttribute("error", "Link not found");
            return "errorPage";
        }

        var link = linkOpt.get();

        if (result.hasErrors()) {
            model.addAttribute("link", linkUpdate);
            model.addAttribute("linkId", id);
            model.addAttribute("editable", true);
            return "manageLink";
        }

        try {
            linkService.updateLinkFromView(id, linkUpdate);
        } catch (NotFoundExcepiton e) {
            return "notFound";
        }

        return "redirect:/manage/" + id;
    }

    @PostMapping("/manage/{id}/delete")
    public String deleteLink(@PathVariable String id,
                             Model model,
                             HttpSession session) {

        var linkOpt = linkService.getLink(id);
        if (linkOpt.isEmpty()) {
            model.addAttribute("error", "Link not found");
            return "errorPage";
        }

        var link = linkOpt.get();

        @SuppressWarnings("unchecked")
        Set<String> authorizedLinks = (Set<String>) session.getAttribute("authorizedLinks");
        boolean editable = authorizedLinks != null && authorizedLinks.contains(id);

        try {
            linkService.deleteLinkFromView(id);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("link", link);
            model.addAttribute("linkId", id);
            model.addAttribute("editable", editable);
            model.addAttribute("error", "Failed to delete link: " + e.getMessage());
            return "manageLink";
        }
    }

    @GetMapping("/notFound")
    public String linkNotFound() {
        return "notFound";
    }


    @GetMapping("/searchLink")
    public String showSearchForm() {
        return "searchLink";
    }

    @PostMapping("/searchLink")
    public String handleSearch(@RequestParam("linkId") String linkId, RedirectAttributes redirectAttributes) {
        if (linkId == null || linkId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter a valid Link ID.");
            return "redirect:/searchLink";
        }

        return "redirect:/manage/" + linkId.trim();
    }
}
