package pl.edu.pja.sladan.tpo_02.dependency_inversion_principle;

public class SMSProducer implements MessageProducer {

    @Override
    public String getMessage() {
        return "My SMS No. " + (int)(Math.random()*1000);
    }
}
