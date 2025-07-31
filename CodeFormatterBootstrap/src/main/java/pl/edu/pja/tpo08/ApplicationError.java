package pl.edu.pja.tpo08;

public class ApplicationError {
    private String title;
    private String message;

    public ApplicationError(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public ApplicationError(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
