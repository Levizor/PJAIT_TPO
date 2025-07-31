package pl.edu.pja.tpo09.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pja.tpo09.Models.BMRResponse;
import pl.edu.pja.tpo09.Models.InvalidDataException;
import pl.edu.pja.tpo09.Models.InvalidGenderDataException;
import pl.edu.pja.tpo09.Services.BMRService;

@RestController
@RequestMapping(
        path = "/api/v1/BMR",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE
        }
)
public class BMRController {


    BMRService bmrService;

    public BMRController(BMRService bmrService) {
        this.bmrService = bmrService;
    }

    @GetMapping(value = "/{gender}")
    public ResponseEntity<?> getBMR(
            @RequestHeader(name="Accept", defaultValue = MediaType.APPLICATION_JSON_VALUE) String accept,
            @PathVariable String gender,
            @RequestParam float weight,
            @RequestParam float height,
            @RequestParam int age
    ){
        try {
            var bmr = (int) bmrService.BMR(gender, age, weight, height);
            if (accept.contains("text/plain")){
                return ResponseEntity.ok(String.format("%dkcal", bmr));
            }

            BMRResponse response = new BMRResponse();
            response.setBmr(bmr);
            response.setWeight(weight);
            response.setHeight(height);
            response.setGender(gender);
            response.setAge(age);
            return ResponseEntity.ok(response);
        } catch (InvalidGenderDataException e){
            return ResponseEntity.badRequest().header("Reason", e.getMessage()).build();
        } catch (InvalidDataException e){
            return ResponseEntity.status(499).header("Reason", e.getMessage()).build();
        }
    }
}
