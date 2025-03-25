package pl.edu.pja.sladan.tpo_02.dependency_injection;

public class MailProducer {

    public String getMessage(){
        return "My Mail No. " + (int)(Math.random()*1000);
    }

}
