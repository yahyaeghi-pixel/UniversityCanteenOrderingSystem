package LoginPackage;

import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n===== University Canteen System =====");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    LoginApp.main(null);  
                    break;

                case 2:
                    CreateAccountApp.main(null);
                    break;

                case 3:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2, or 3.");
            }
        }

        sc.close();
    }
}
