package pl.edu.pja.sladan.tpo_02.dependency_inversion_principle;

import java.io.*;

public class FilePrinter implements MessagePrinter{

    private MessageProducer MessageProducer;

    public FilePrinter(MessageProducer MessageProducer){
        this.MessageProducer = MessageProducer;
    }

    @Override
    public void printMessage(){
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("messages.txt"), true))) {
            pw.println(MessageProducer.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
