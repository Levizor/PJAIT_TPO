package pl.edu.pja.tpo_07;

import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;
import com.google.googlejavaformat.java.Formatter;

@Service
public class FormatterService {
    private final Formatter formatter;

    public FormatterService(Formatter formatter) {
        this.formatter = formatter;
    }

    public String format(String input) throws FormatterException {
        return formatter.formatSource(input);
    }

    public void format(Code code) throws FormatterException {
        code.setFormatted(formatter.formatSource(code.getRaw()));
    }
}
