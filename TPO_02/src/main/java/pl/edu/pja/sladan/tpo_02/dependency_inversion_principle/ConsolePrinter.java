package pl.edu.pja.sladan.tpo_02.dependency_inversion_principle;

public class ConsolePrinter implements MessagePrinter{

    private MessageProducer MessageProducer;

    public ConsolePrinter(MessageProducer MessageProducer){
        this.MessageProducer = MessageProducer;
    }

    @Override
    public void printMessage(){
        System.out.println(MessageProducer.getMessage());
    }
}
