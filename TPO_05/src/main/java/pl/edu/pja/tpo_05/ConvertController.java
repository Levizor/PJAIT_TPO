package pl.edu.pja.tpo_05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConvertController {

    @PostMapping("/convert")
    @ResponseBody
    public String convert(@RequestParam String value, @RequestParam int fromBase, @RequestParam int toBase) {
        var builder = new StringBuilder();
        builder.append("<html><body style='font-family: Arial, sans-serif;'>");
        builder.append("<div style='max-width: 600px; margin: 30px auto; text-align: center;'>");

        try {
            var converted = Converter.convert(value, fromBase, toBase);

            builder.append("<h2>Conversion Result</h2>");
            builder.append(String.format(
                    "<p><strong>%s</strong> <span style='color: gray;'>[base %d]</span> &rarr; " +
                            "<strong><span style='font-size: 1.2em;'>%s</span></strong> <span style='color: gray;'>[base %d]</span></p>",
                    value, fromBase, converted, toBase
            ));

            builder.append("<hr>");
            builder.append("<h3>Value in Common Bases:</h3>");
            builder.append("<ul style='list-style: none; padding-left: 0;'>");
            for (var system : new int[]{2, 8, 10, 16}) {
                var conv = Converter.convert(value, fromBase, system);
                builder.append(String.format(
                        "<li><code><strong>Base %d:</strong> %s</code></li>",
                        system, conv
                ));
            }
            builder.append("</ul>");

        } catch (Exception ex) {
            builder.append(String.format(
                    "<p style='color: red;'>Error: %s</p>",
                    ex.getMessage()
            ));
        }

        builder.append("</div></body></html>");
        return builder.toString();
    }
}
