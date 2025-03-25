package pl.edu.pja.sladan.tpo_02.dependency_inversion_principle;

public class MailProducer implements MessageProducer {

    @Override
    public String getMessage() {
        return "My Mail No. " + (int)(Math.random()*1000);
    }
}
