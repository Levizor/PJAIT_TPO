package pl.edu.pja.tpo09.Controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pja.tpo09.Models.BMIResponse;
import pl.edu.pja.tpo09.Models.BMIType;
import pl.edu.pja.tpo09.Models.InvalidDataException;
import pl.edu.pja.tpo09.Services.BMIService;

@RestController
@RequestMapping(
        path = "/api/v1/BMI",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE
        }
)
public class BMIController {

    BMIService bmiService;

    public BMIController(BMIService bmiService) {
        this.bmiService = bmiService;
    }

    @GetMapping(
    )
    public ResponseEntity<?> getBMIResponse(
        @RequestHeader(name = "Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String accept,
        @RequestParam float weight,
        @RequestParam float height
    ) {
        try {
            var bmi = (int) bmiService.BMI(weight, height);
            if (accept.contains("text/plain")){
                return ResponseEntity.ok(String.valueOf(bmi));
            }
            var response = new BMIResponse();
            response.setWeight(weight);
            response.setHeight(height);
            response.setBmi(bmi);
            response.setType(BMIType.fromBMI(bmi).name());

            return ResponseEntity.ok(response);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().header("Reason", e.getMessage()).build();
        }
    }

}
