package LoginPackage;

import java.util.Scanner;

public class CreateAccountApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AccountCreationService service = new AccountCreationService();

        System.out.println("===== Create New Account =====");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.print("Enter Role (student/lecturer): ");
        String role = sc.nextLine().toLowerCase();

        if (!role.equals("student") && !role.equals("lecturer")) {
            System.out.println("Invalid role. Must be 'student' or 'lecturer'.");
            sc.close();
            return;
        }

        boolean success = service.createAccount(email, password, role);

        if (success) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed.");
        }

        sc.close();
    }
}
