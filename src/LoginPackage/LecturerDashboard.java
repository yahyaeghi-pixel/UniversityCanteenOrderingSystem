package LoginPackage;

import java.util.Scanner;

public class LecturerDashboard {

    public static void showDashboard(String lecturerEmail) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 6) {

            System.out.println("\n===== Lecturer Dashboard =====");
            System.out.println("Logged in as: " + lecturerEmail);
            System.out.println("1. View Today’s Menu");
            System.out.println("2. Give Feedback");
            System.out.println("3. View Previous Orders");
            System.out.println("4. Add Menu Item (Staff Only)");
            System.out.println("5. Remove Menu Item (Staff Only)");
            System.out.println("6. Logout");
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
                    giveFeedback(lecturerEmail);
                    break;

                case 3:
                    viewPreviousOrders(lecturerEmail);
                    break;

                case 4:
                    addMenuItem();
                    break;

                case 5:
                    removeMenuItem();
                    break;

                case 6:
                    System.out.println("Logging out...");
                    return; // returns to MainMenu
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Placeholder method: you will later replace this with real menu data
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


    // Simple feedback submission for now — later we can save to file
    private static void giveFeedback(String email) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== Submit Feedback =====");
        System.out.println("Write your feedback message:");

        String msg = sc.nextLine();
        String date = java.time.LocalDateTime.now().toString();

        Feedback fb = new Feedback(email, msg, date);
        FeedbackDatabase.saveFeedback(fb);

        System.out.println("Thank you! Your feedback has been submitted.");
    }


    // Placeholder — later this will load real order history
    private static void viewPreviousOrders(String email) {
        System.out.println("\n===== Your Previous Orders =====");
        System.out.println("(Placeholder) No previous orders found.");
    }
    
    // Placeholder method to add menu item
    private static void addMenuItem() {
        Scanner sc = new Scanner(System.in);

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

        // Load current menu
        var menu = MenuDatabase.loadMenu();

        // Add new item
        menu.add(new MenuItem(name, price));

        // Save whole menu again
        MenuDatabase.saveAllMenu(menu);

        System.out.println("Menu item added successfully!");
    }

    // Placeholder method to remove menu item
    private static void removeMenuItem() {

        var menu = MenuDatabase.loadMenu();

        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.println("\n===== Remove Menu Item =====");

        // Display menu with numbers
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName() +
                               " – €" + menu.get(i).getPrice());
        }

        System.out.print("Enter the item number to remove: ");
        Scanner sc = new Scanner(System.in);

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

}
