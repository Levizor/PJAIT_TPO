package pl.edu.pja.sladan.tpo_02.profiles.producer;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.edu.pja.sladan.tpo_02.profiles.MessageProducer;

@Service
@Profile("Mail")
public class MailProducer implements MessageProducer {

    @Override
    public String getMessage() {
        return "My Mail No. " + (int)(Math.random()*1000);
    }
}
