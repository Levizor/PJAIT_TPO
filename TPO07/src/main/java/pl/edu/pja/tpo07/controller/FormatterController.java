package pl.edu.pja.tpo07.controller;

import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.edu.pja.tpo07.ApplicationError;
import pl.edu.pja.tpo07.Code;
import pl.edu.pja.tpo07.service.CodeService;
import pl.edu.pja.tpo07.service.FormatterService;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Controller
public class FormatterController {
    private FormatterService formatterService;
    private CodeService codeService;

    public FormatterController(FormatterService formatterService, CodeService codeService) {
        this.formatterService = formatterService;
        this.codeService = codeService;
    }

    @ModelAttribute("timeUnits")
    public TimeUnit[] getTimeUnits() {
        return Arrays
                .stream(TimeUnit.values())
                .filter(t -> t != TimeUnit.NANOSECONDS && t != TimeUnit.MILLISECONDS && t != TimeUnit.MICROSECONDS)
                .toArray(TimeUnit[]::new);
    }

    @GetMapping("/formatter")
    public String formatter(Model model) {
        model.addAttribute("code", new Code());
        return "formatter";
    }

    @PostMapping("/formatter")
    public String formatter(Code code, Model model) {
        try {
            formatterService.format(code);
        } catch (FormatterException e) {
            model.addAttribute("error", new ApplicationError("An error occurred while formatting", e.getMessage()));
            return "formatter";
        }
        model.addAttribute("code", code);
        return "formatter";
    }

    @GetMapping("/code")
    public String code(@RequestParam String id, Model model) {
        var code = codeService.findById(id);
        if (code.isEmpty()) {
            return "redirect:/invalidID";
        }
        model.addAttribute("code", code.get());
        return "code";
    }

    // initially I used redirect view as a return type on this method,
    // but for the better user experients I put errors inside formatter, now method is a bit cluttered
    @PostMapping("/save")
    public String save(Code code, Model model) {
        if (code.getId() == null || code.getKeepFor() == null || code.getTimeUnit() == null) {
            model.addAttribute("error", new ApplicationError("Form not filled", "Please fill all the form parameters before submitting"));
            return "formatter";
        }
        if (code.getTimeUnit().toDays(code.getKeepFor()) > 90) {
            model.addAttribute("error", new ApplicationError("Duration too long", "Please specify a duration no less than 10 seconds and no greater than 90 days"));
            return "formatter";
        }
        try {
            formatterService.format(code);
        } catch (FormatterException e) {
            model.addAttribute("error", new ApplicationError("An error occurred while formatting", e.getMessage()));
            return "formatter";
        }
        if (codeService.save(code)) {
            return "redirect:/code?id=" + code.getId();
        }
        model.addAttribute("error", new ApplicationError("ID is already in use", "Please choose another ID"));
        return "formatter";
    }

    @GetMapping("invalidID")
    public String invalidID() {
        return "invalidID";
    }
}
