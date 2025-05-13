package pl.edu.pja.tpo_06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pja.tpo_06.DTO.FormData;
import pl.edu.pja.tpo_06.service.FakeDataService;
import pl.edu.pja.tpo_06.service.LocalizerService;

@Controller
public class GeneratorController {
    FakeDataService fakeDataService;
    LocalizerService localizerService;

    public GeneratorController(FakeDataService fakeDataService, LocalizerService localizerService) {
        this.fakeDataService = fakeDataService;
        this.localizerService = localizerService;
    }

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("formData", new FormData());
        return "index";
    }

    @PostMapping("/")
    public String generator(@ModelAttribute FormData formData, Model model) {
        try {
            var people = fakeDataService.generatePeople(formData);
            var headers = localizerService.getLocalizedHeaders(formData);
            model.addAttribute("headers", headers);
            model.addAttribute("people", people);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "index";
    }
}
