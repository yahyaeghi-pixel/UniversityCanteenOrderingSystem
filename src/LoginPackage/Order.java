package LoginPackage;

public class Order {
    private String email;
    private String itemName;
    private double itemPrice;
    private String date;
    private String status; // NEW

    public Order(String email, String itemName, double itemPrice, String date, String status) {
        this.email = email;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.date = date;
        this.status = status;
    }

    public String getEmail() { return email; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}
