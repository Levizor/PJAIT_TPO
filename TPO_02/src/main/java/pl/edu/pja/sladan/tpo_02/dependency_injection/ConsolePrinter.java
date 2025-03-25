package pl.edu.pja.sladan.tpo_02.dependency_injection;

public class ConsolePrinter {

    private MailProducer mailProducer;

    public ConsolePrinter(MailProducer mailProducer){
        this.mailProducer = mailProducer;
    }
    public void printMessage(){
        System.out.println(mailProducer.getMessage());
    }
}
