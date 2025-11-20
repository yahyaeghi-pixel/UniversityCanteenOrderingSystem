package LoginPackage;

import java.io.*;
import java.util.ArrayList;

public class OrderDatabase {

    private static final String FILE_NAME = "orders.txt";

    public static void saveOrder(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(order.getEmail() + "," +
                     order.getItemName() + "," +
                     order.getItemPrice() + "," +
                     order.getDate() + "," +
                     order.getStatus());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving order.");
        }
    }

    public static ArrayList<Order> loadOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                if (p.length == 5) {
                    orders.add(new Order(
                            p[0],                   // email
                            p[1],                   // item name
                            Double.parseDouble(p[2]), // price
                            p[3],                   // date
                            p[4]                    // status
                    ));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading orders.");
        }

        return orders;
    }


    public static void saveAllOrders(ArrayList<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Order o : orders) {
                bw.write(o.getEmail() + "," +
                         o.getItemName() + "," +
                         o.getItemPrice() + "," +
                         o.getDate() + "," +
                         o.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders.");
        }
    }
}
