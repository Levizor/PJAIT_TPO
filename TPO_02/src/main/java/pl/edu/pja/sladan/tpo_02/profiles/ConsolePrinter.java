package pl.edu.pja.sladan.tpo_02.profiles;


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
