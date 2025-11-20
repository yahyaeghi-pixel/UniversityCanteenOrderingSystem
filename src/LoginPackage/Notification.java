package LoginPackage;

public class Notification {
    private String email;
    private String message;
    private String date;

    public Notification(String email, String message, String date) {
        this.email = email;
        this.message = message;
        this.date = date;
    }

    public String getEmail() { return email; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
}
