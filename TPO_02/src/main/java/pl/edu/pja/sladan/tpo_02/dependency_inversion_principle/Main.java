package pl.edu.pja.sladan.tpo_02.dependency_inversion_principle;

public class Main {

    public static void main(String[] args) {
        MessageProducer messageProducer1 = new MailProducer();
        MessageProducer messageProducer2 = new SMSProducer();

        MessagePrinter printer1 = new ConsolePrinter(messageProducer1);
        printer1.printMessage();
        MessagePrinter printer2 = new ConsolePrinter(messageProducer2);
        printer2.printMessage();

        MessagePrinter printer3 = new FilePrinter(messageProducer1);
        printer3.printMessage();

    }

}
