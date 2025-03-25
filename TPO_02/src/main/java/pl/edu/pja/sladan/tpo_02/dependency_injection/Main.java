package pl.edu.pja.sladan.tpo_02.dependency_injection;

public class Main {

    public static void main(String[] args) {
        MailProducer mailProducer = new MailProducer();
        ConsolePrinter consolePrinter = new ConsolePrinter(mailProducer);
        consolePrinter.printMessage();

    }

}
