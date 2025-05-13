package pl.edu.pja.tpo07.service;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;
import pl.edu.pja.tpo07.Code;

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
