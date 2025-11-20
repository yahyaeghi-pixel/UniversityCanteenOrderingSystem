package LoginPackage;

import java.util.ArrayList;
import java.util.Scanner;

public class StaffFeedbackDashboard {

    public static void showDashboard() {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {

            System.out.println("\n===== Staff Feedback Management =====");
            System.out.println("1. View All Feedback");
            System.out.println("2. Search Feedback by Email");
            System.out.println("3. Delete Feedback");
            System.out.println("4. Back to Staff Main Menu");
            System.out.print("Choose an option: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewAllFeedback();
                    break;

                case 2:
                    searchFeedbackByEmail();
                    break;

                case 3:
                    deleteFeedback();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void viewAllFeedback() {

        ArrayList<Feedback> list = FeedbackDatabase.loadFeedback();

        System.out.println("\n===== All Feedback =====");

        if (list.isEmpty()) {
            System.out.println("No feedback found.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Feedback f = list.get(i);
            System.out.println((i + 1) + ". " + f.getEmail());
            System.out.println("   \"" + f.getMessage() + "\"");
            System.out.println("   Date: " + f.getDate());
            System.out.println();
        }
    }


    private static void searchFeedbackByEmail() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        ArrayList<Feedback> list = FeedbackDatabase.loadFeedback();
        boolean found = false;

        System.out.println("\n===== Feedback from " + email + " =====");

        for (Feedback f : list) {
            if (f.getEmail().equalsIgnoreCase(email)) {
                found = true;
                System.out.println("- \"" + f.getMessage() + "\" on " + f.getDate());
            }
        }

        if (!found) {
            System.out.println("No feedback found for this email.");
        }
    }


    private static void deleteFeedback() {

        ArrayList<Feedback> list = FeedbackDatabase.loadFeedback();

        if (list.isEmpty()) {
            System.out.println("No feedback to delete.");
            return;
        }

        viewAllFeedback();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter feedback number to delete: ");

        int index;

        try {
            index = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        if (index < 1 || index > list.size()) {
            System.out.println("Invalid number.");
            return;
        }

        Feedback removed = list.remove(index - 1);
        FeedbackDatabase.saveAllFeedback(list);

        System.out.println("Deleted feedback from: " + removed.getEmail());
    }
}
