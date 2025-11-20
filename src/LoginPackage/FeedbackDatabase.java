package LoginPackage;

import java.io.*;
import java.util.ArrayList;

public class FeedbackDatabase {

    private static final String FILE_NAME = "feedback.txt";

    public static void saveFeedback(Feedback f) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            bw.write(f.getEmail() + "|" + f.getMessage() + "|" + f.getDate());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving feedback.");
        }
    }

    public static ArrayList<Feedback> loadFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");

                if (p.length == 3) {
                    list.add(new Feedback(p[0], p[1], p[2]));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading feedback.");
        }

        return list;
    }

    public static void saveAllFeedback(ArrayList<Feedback> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Feedback f : list) {
                bw.write(f.getEmail() + "|" + f.getMessage() + "|" + f.getDate());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating feedback.");
        }
    }
}
