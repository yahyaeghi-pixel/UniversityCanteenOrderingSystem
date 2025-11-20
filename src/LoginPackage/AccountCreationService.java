package LoginPackage;

import java.util.ArrayList;

public class AccountCreationService {

    public boolean emailExists(String email) {
        ArrayList<User> users = UserDatabase.loadUsers();

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 4; // simple student-level rule
    }

    public boolean createAccount(String email, String password, String role) {

        if (emailExists(email)) {
            System.out.println("Error: Email already exists.");
            return false;
        }

        if (!isValidPassword(password)) {
            System.out.println("Error: Password must be at least 4 characters.");
            return false;
        }

        User newUser = new User(email, password, role);
        UserDatabase.saveUser(newUser);

        return true;
    }
}
