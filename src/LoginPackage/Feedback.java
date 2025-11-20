package LoginPackage;

public class Feedback {
    private String email;
    private String message;
    private String date;

    public Feedback(String email, String message, String date) {
        this.email = email;
        this.message = message;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
