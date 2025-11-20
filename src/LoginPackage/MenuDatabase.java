package LoginPackage;

import java.io.*;
import java.util.ArrayList;

public class MenuDatabase {

    private static final String FILE_NAME = "menu.txt";

    public static ArrayList<MenuItem> loadMenu() {
        ArrayList<MenuItem> menu = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    String name = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    menu.add(new MenuItem(name, price));
                }
            }

        } catch (IOException e) {
            System.out.println("Error: menu.txt file not found.");
        }

        return menu;
    }
    public static void saveAllMenu(ArrayList<MenuItem> menu) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (MenuItem item : menu) {
                bw.write(item.getName() + "," + item.getPrice());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving menu.");
        }
    }

}
