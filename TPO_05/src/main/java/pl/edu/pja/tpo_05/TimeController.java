package pl.edu.pja.tpo_05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Year;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    @ResponseBody
    public String time(
            @RequestParam(required = false, defaultValue = "Z") String timezone,
            @RequestParam(required = false, defaultValue = "HH:mm:ss.SSSS YYYY/MM/dd") String format
    ) {
        Date currentTime = new Date();
        String response = "";
        String warning = "";
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(format);
            ZoneId zone = ZoneId.of(timezone);
            formatter.setTimeZone(TimeZone.getTimeZone(zone));
        }catch (IllegalArgumentException e){
            formatter = new SimpleDateFormat("HH:mm:ss.SSSS YYYY/MM/dd");
            warning += "Invalid date format provided. Changing to defaulting one.";
        }catch (DateTimeException e){
            warning += "Invalid time zone provided. Defaulting to system time zone.";
        } finally {
            response = String.format("%s", formatter.format(currentTime));
        }
        response = String.format("<h1>%s</h1>\n\n<p style=\"color:red;\">%s</p>", response, warning);
        return response;
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String currentYear(){
        return String.valueOf(Year.now());
    }

}
