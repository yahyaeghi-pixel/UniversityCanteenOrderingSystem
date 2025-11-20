package LoginPackage;

import java.util.ArrayList;

public class LoginService {

    private ArrayList<User> users;

    public LoginService() {
        users = UserDatabase.loadUsers();
    }

    public User login(String email, String password) {

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) &&
                user.getPassword().equals(password)) {
                return user; // Login success
            }
        }

        return null; // Login failed
    }
}
