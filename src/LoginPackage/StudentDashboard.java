package LoginPackage;


import java.util.ArrayList;
import java.util.Scanner;

public class StudentDashboard {

	private static ArrayList<BasketItem> basket = new ArrayList<>();

    public static void showDashboard(String studentEmail) {

        Scanner sc = Console.IN;
        int choice = 0;

        while (choice != 8) {

            System.out.println("\n===== Student Dashboard =====");
            System.out.println("Logged in as: " + studentEmail);
            System.out.println("1. View Today’s Menu");
            System.out.println("2. Add Item to Basket");
            System.out.println("3. View Basket");
            System.out.println("4. Checkout");
            System.out.println("5. View My Orders");
            System.out.println("6. View Notifications");
            System.out.println("7. Give Feedback");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    viewMenu();
                    break;

                case 2:
                    addItemToBasket();
                    break;

                case 3:
                    viewBasket();
                    break;

                case 4:
                    checkout(studentEmail);
                    break;

                case 5:
                    viewMyOrders(studentEmail);
                    break;

                case 6:
                    viewNotifications(studentEmail);
                    break;

                case 7:
                    giveFeedback(studentEmail);
                    break;

                case 8:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Placeholder: You can expand this later
    private static void viewMenu() {

        System.out.println("\n===== Today’s Menu =====");

        var menuItems = MenuDatabase.loadMenu();

        if (menuItems.isEmpty()) {
            System.out.println("No menu items found.");
            return;
        }

        for (MenuItem item : menuItems) {
            System.out.println("• " + item.getName() + " – €" + item.getPrice());
        }
    }
    
    // Placeholder for later order history
    private static void viewMyOrders(String email) {

        System.out.println("\n===== My Orders =====");

        var orders = OrderDatabase.loadOrders();
        boolean found = false;

        for (Order o : orders) {
            if (o.getEmail().equalsIgnoreCase(email)) {
                found = true;
                System.out.println("• " + o.getItemName() +
                                   " (€" + o.getItemPrice() + ")" +
                                   " on " + o.getDate() +
                                   " | Status: " + o.getStatus());
            }
        }

        if (!found) {
            System.out.println("You have no previous orders.");
        }
    }

    
    // View items in the basket
    private static void addItemToBasket() {

        var menu = MenuDatabase.loadMenu();

        if (menu.isEmpty()) {
            System.out.println("Menu not available.");
            return;
        }

        System.out.println("\n===== Add Item to Basket =====");

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " – €" + item.getPrice());
        }

        System.out.print("Choose item number: ");
        Scanner sc = Console.IN;
        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid choice.");
            return;
        }

        if (choice < 1 || choice > menu.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        MenuItem selected = menu.get(choice - 1);
        basket.add(new BasketItem(selected.getName(), selected.getPrice()));

        System.out.println("Added to basket: " + selected.getName());
    }
    
    // Checkout and clear the basket
    private static void viewBasket() {

        Scanner sc = Console.IN;

        System.out.println("\n===== Your Basket =====");

        if (basket.isEmpty()) {
            System.out.println("Your basket is empty.");
            return;
        }

        double total = 0;

        // Display items in basket
        for (int i = 0; i < basket.size(); i++) {
            BasketItem item = basket.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " – €" + item.getPrice());
            total += item.getPrice();
        }

        System.out.println("Total: €" + total);

        // Ask if user wants to remove an item
        System.out.print("Remove an item? (yes/no): ");
        String ans = sc.nextLine();

        if (ans.equalsIgnoreCase("yes")) {
            System.out.print("Enter item number to remove: ");

            try {
                int removeIndex = Integer.parseInt(sc.nextLine());

                if (removeIndex >= 1 && removeIndex <= basket.size()) {
                    BasketItem removedItem = basket.remove(removeIndex - 1);
                    System.out.println("Removed: " + removedItem.getName());
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    
    // Checkout process
    private static void checkout(String email) {

        if (basket.isEmpty()) {
            System.out.println("Your basket is empty.");
            return;
        }

        System.out.println("\n===== Checkout =====");

        double total = 0;
        for (BasketItem item : basket) {
            total += item.getPrice();
        }

        System.out.println("Total amount: €" + total);

        System.out.print("Proceed to payment? (yes/no): ");
        Scanner sc = Console.IN;
        String ans = sc.nextLine();

        if (!ans.equalsIgnoreCase("yes")) {
            System.out.println("Checkout cancelled.");
            return;
        }

        // CALL PAYMENT SYSTEM
        boolean paymentSuccess = PaymentService.processPayment(total);

        if (!paymentSuccess) {
            System.out.println("Payment failed. Try again.");
            return;
        }

        // SAVE ORDERS ONLY IF PAYMENT SUCCESSFUL
        String datetime = java.time.LocalDateTime.now().toString();

        for (BasketItem item : basket) {
        	Order order = new Order(email, item.getName(), item.getPrice(), datetime, "Pending");
            OrderDatabase.saveOrder(order);
        }

        System.out.println("Order completed! Your items have been ordered.");
        
        basket.clear(); // empty basket after checkout
    }
    
    // View notifications for the student
    private static void viewNotifications(String email) {

        System.out.println("\n===== Your Notifications =====");

        var list = NotificationDatabase.loadNotifications();
        boolean found = false;

        for (Notification n : list) {
            if (n.getEmail().equalsIgnoreCase(email)) {
                found = true;
                System.out.println("- " + n.getMessage() + " (" + n.getDate() + ")");
            }
        }

        if (!found) {
            System.out.println("No notifications yet.");
        }
    }

    // Later we will save feedback to a file
    private static void giveFeedback(String email) {
        Scanner sc = Console.IN;

        System.out.println("\n===== Submit Feedback =====");
        System.out.println("Write your feedback message:");

        String msg = sc.nextLine();
        String date = java.time.LocalDateTime.now().toString();

        Feedback fb = new Feedback(email, msg, date);
        FeedbackDatabase.saveFeedback(fb);

        System.out.println("Thank you! Your feedback has been submitted.");
    }

}
