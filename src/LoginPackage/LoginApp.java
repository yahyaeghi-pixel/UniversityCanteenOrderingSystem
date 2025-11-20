package LoginPackage;

import java.util.Scanner;

public class LoginApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginService service = new LoginService();

        System.out.println("===== University Canteen Login =====");

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User loggedIn = service.login(email, password);

        if (loggedIn != null) {
            System.out.println("Login successful!");

            if (loggedIn.getRole().equals("lecturer")) {
                LecturerDashboard.showDashboard(loggedIn.getEmail());
            }
            else if (loggedIn.getRole().equals("student")) {
                StudentDashboard.showDashboard(loggedIn.getEmail());
            }


        } else {
            System.out.println("Invalid email or password. Try again.");
        }

        sc.close();
    }
}
