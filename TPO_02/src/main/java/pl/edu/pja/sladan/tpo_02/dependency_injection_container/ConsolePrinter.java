package pl.edu.pja.sladan.tpo_02.dependency_injection_container;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsolePrinter {

    private MessageProducer messageProducer;

//    @Autowired
    public ConsolePrinter(MessageProducer messageProducer){
        this.messageProducer = messageProducer;
    }
    public void printMessage(){
        System.out.println(messageProducer.getMessage());
    }
}
