package LoginPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class StaffDashboard {

    public static void showDashboard() {

        Scanner sc = Console.IN;
        int choice = 0;

        while (choice != 8) {

            System.out.println("\n===== Staff Order Management =====");
            System.out.println("1. View All Orders (Students + Lecturers)");
            System.out.println("2. Search Orders by Email");
            System.out.println("3. Filter Orders by Role");
            System.out.println("4. Update Order Status");
            System.out.println("5. Delete an Order");
            System.out.println("6. Manage Menu (Add/Remove Items)");
            System.out.println("7. Manage Feedback");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:
                    viewAllOrders();
                    break;

                case 2:
                    searchOrdersByEmail();
                    break;

                case 3:
                    filterOrdersByRole();
                    break;

                case 4:
                    updateOrderStatus();
                    break;

                case 5:
                    deleteOrder();
                    break;

                case 6:
                    manageMenu();
                    break;

                case 7:
                    StaffFeedbackDashboard.showDashboard();
                    break;

                case 8:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }


    // ----------------- MANAGE MENU (ADD/REMOVE) -----------------
    private static void manageMenu() {

        Scanner sc = Console.IN;
        int choice = 0;

        while (choice != 3) {

            System.out.println("\n===== Manage Menu =====");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Back to Staff Main Menu");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;

                case 2:
                    removeMenuItem();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addMenuItem() {
        Scanner sc = Console.IN;

        System.out.println("\n===== Add Menu Item =====");
        System.out.print("Enter new item name: ");
        String name = sc.nextLine();

        System.out.print("Enter price (e.g., 6.50): ");
        double price;

        try {
            price = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid price.");
            return;
        }

        var menu = MenuDatabase.loadMenu();
        menu.add(new MenuItem(name, price));
        MenuDatabase.saveAllMenu(menu);

        System.out.println("Menu item added successfully!");
    }

    private static void removeMenuItem() {

        var menu = MenuDatabase.loadMenu();

        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.println("\n===== Remove Menu Item =====");

        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName() +
                               " – €" + menu.get(i).getPrice());
        }

        System.out.print("Enter the item number to remove: ");
        Scanner sc = Console.IN;

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        if (choice < 1 || choice > menu.size()) {
            System.out.println("Invalid item number.");
            return;
        }

        MenuItem removed = menu.remove(choice - 1);
        MenuDatabase.saveAllMenu(menu);

        System.out.println("Removed item: " + removed.getName());
    }


    // ----------------- VIEW ALL ORDERS -----------------
    private static void viewAllOrders() {

        ArrayList<Order> orders = OrderDatabase.loadOrders();

        System.out.println("\n===== All Orders (Students + Lecturers) =====");

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println((i + 1) + ". " +
                    o.getEmail() + " ordered " +
                    o.getItemName() + " (€" + o.getItemPrice() + ")" +
                    " on " + o.getDate());
        }
    }


    // ----------------- SEARCH BY EMAIL -----------------
    private static void searchOrdersByEmail() {

        Scanner sc = Console.IN;
        System.out.print("Enter email to search: ");
        String email = sc.nextLine();

        ArrayList<Order> orders = OrderDatabase.loadOrders();
        boolean found = false;

        System.out.println("\n===== Orders for " + email + " =====");

        for (Order o : orders) {
            if (o.getEmail().equalsIgnoreCase(email)) {
                found = true;
                System.out.println("• " + o.getItemName() + " (€" + o.getItemPrice() + ") on " + o.getDate());
            }
        }

        if (!found) {
            System.out.println("No orders found for this email.");
        }
    }


    // ----------------- FILTER BY ROLE (student / lecturer) -----------------
    private static void filterOrdersByRole() {

        Scanner sc = Console.IN;
        System.out.print("Enter role to filter (student/lecturer): ");
        String role = sc.nextLine().toLowerCase();

        if (!role.equals("student") && !role.equals("lecturer")) {
            System.out.println("Invalid role.");
            return;
        }

        ArrayList<Order> orders = OrderDatabase.loadOrders();
        ArrayList<User> users = UserDatabase.loadUsers();

        System.out.println("\n===== Orders from " + role + "s =====");

        boolean found = false;

        for (Order o : orders) {
            for (User u : users) {
                if (u.getEmail().equalsIgnoreCase(o.getEmail()) &&
                    u.getRole().equalsIgnoreCase(role)) {

                    found = true;
                    System.out.println("• " + o.getEmail() + " ordered " +
                                       o.getItemName() + " (€" + o.getItemPrice() + ")" +
                                       " on " + o.getDate());
                }
            }
        }

        if (!found) {
            System.out.println("No " + role + " orders found.");
        }
    }

    // ----------------- UPDATE ORDER STATUS -----------------
    private static void updateOrderStatus() {

        ArrayList<Order> orders = OrderDatabase.loadOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        viewAllOrders();

        Scanner sc = Console.IN;
        System.out.print("Enter order number to update: ");

        int index;

        try {
            index = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        if (index < 1 || index > orders.size()) {
            System.out.println("Invalid order number.");
            return;
        }

        Order order = orders.get(index - 1);

        System.out.println("\nCurrent status: " + order.getStatus());
        System.out.println("Choose new status:");
        System.out.println("1. Pending");
        System.out.println("2. Preparing");
        System.out.println("3. Ready");
        System.out.println("4. Completed");
        System.out.print("Your choice: ");

        int statusChoice;

        try {
            statusChoice = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        String newStatus = switch (statusChoice) {
            case 1 -> "Pending";
            case 2 -> "Preparing";
            case 3 -> "Ready";
            case 4 -> "Completed";
            default -> null;
        };

        if (newStatus == null) {
            System.out.println("Invalid status.");
            return;
        }

        order.setStatus(newStatus);
        OrderDatabase.saveAllOrders(orders);

        System.out.println("Order status updated successfully!");
       
        if (newStatus.equals("Ready")) {
            String msg = "Your order for " + order.getItemName() + " is READY!";
            String date = java.time.LocalDateTime.now().toString();

            Notification n = new Notification(order.getEmail(), msg, date);
            NotificationDatabase.saveNotification(n);

            System.out.println("Notification sent to: " + order.getEmail());
        }

    }

    // ----------------- DELETE ORDER -----------------
    private static void deleteOrder() {

        ArrayList<Order> orders = OrderDatabase.loadOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders to delete.");
            return;
        }

        viewAllOrders();

        Scanner sc = Console.IN;
        System.out.print("Enter order number to delete: ");

        int index;

        try {
            index = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        if (index < 1 || index > orders.size()) {
            System.out.println("Invalid order number.");
            return;
        }

        Order removed = orders.remove(index - 1);

        OrderDatabase.saveAllOrders(orders);

        System.out.println("Deleted order: " + removed.getItemName() + " from " + removed.getEmail());
    }
}
