package LoginPackage;

import java.io.*;
import java.util.ArrayList;

public class UserDatabase {

    private static final String FILE_NAME = "users.txt";

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }

        } catch (IOException e) {
            System.out.println("No user database found.");
        }

        return users;
    }

    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(user.getEmail() + "," + user.getPassword() + "," + user.getRole());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user.");
        }
    }
}
