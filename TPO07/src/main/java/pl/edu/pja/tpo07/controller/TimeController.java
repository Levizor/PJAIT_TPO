package pl.edu.pja.tpo07.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pl.edu.pja.tpo07.service.CodeService;

@Controller
public class TimeController {
    CodeService codeService;

    public TimeController(CodeService codeService) {
        this.codeService = codeService;
    }

    // Why threads?
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        deserialize();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    break;
                }
                updateCodeRepository();
            }
        }).start();
    }

    // this is better
//    @PostConstruct // at the startup of the application
    void deserialize(){
        codeService.deserialize();
        codeService.removeExpired();
        codeService.serialize();
    }

//    @Scheduled(fixedRate = 10_000) // each 10 seconds
    void updateCodeRepository(){
        codeService.removeExpired();
        codeService.serialize();
    }

}
