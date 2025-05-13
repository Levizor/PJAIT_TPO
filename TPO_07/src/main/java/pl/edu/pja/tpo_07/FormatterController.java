package pl.edu.pja.tpo_07;

import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FormatterController {
    private CodeService codeService;
    private FormatterService formatterService;

    public FormatterController(CodeService codeService, FormatterService formatterService) {
        this.codeService = codeService;
        this.formatterService = formatterService;
    }

    @GetMapping("/formatter")
    public String formatter(Model model) {
        model.addAttribute("code", new Code());
        return "addCode";
    }

    @GetMapping("/invalidID")
    public String invalidID() {
        return "invalidID";
    }

    @GetMapping("/usedID")
    public String usedID() {
        return "usedID";
    }

    @GetMapping("/formatException")
    public String formatException(@RequestParam(required = false) String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "formatException";
    }

    @GetMapping("/code")
    public String code(@RequestParam String id, Model model) {
        var code = codeService.findById(id);
        if (code.isPresent()){
            model.addAttribute("code", code);
            return "code";
        }
        return "invalidID";
    }

    @PostMapping("/format")
    public RedirectView format(Code code) {
        try {
            formatterService.format(code);
        } catch (FormatterException e) {
            RedirectView redirectView = new RedirectView("/formatException");
            redirectView.addStaticAttribute("errorMessage", e.getMessage());
            return redirectView;
        }

        if (codeService.save(code)) {
            return new RedirectView("code?id=" + code.getId(), true, false);
        }
        return new RedirectView("usedID", true, false);
    }

}
