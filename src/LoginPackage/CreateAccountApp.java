package LoginPackage;

import java.util.Scanner;

public class CreateAccountApp {

    public static void main(String[] args) {

        Scanner sc = Console.IN;
        AccountCreationService service = new AccountCreationService();

        System.out.println("===== Create New Account =====");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.print("Enter Role (student/lecturer/staff): ");
        String role = sc.nextLine().toLowerCase();

        if (!role.equals("student") && !role.equals("lecturer") && !role.equals("staff")) {
            System.out.println("Invalid role. Must be 'student', 'lecturer', or 'staff'.");
            return;
        }

        boolean success = service.createAccount(email, password, role);

        if (success) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed.");
        }

    }
}
