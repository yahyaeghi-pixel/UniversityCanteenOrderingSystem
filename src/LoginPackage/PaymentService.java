package LoginPackage;

import java.util.Scanner;

public class PaymentService {

    public static boolean processPayment(double totalAmount) {

        Scanner sc = Console.IN;

        System.out.println("\n===== Payment Section =====");
        System.out.println("Total Amount: €" + totalAmount);

        System.out.print("Enter Cardholder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Card Number (16 digits): ");
        String cardNumber = sc.nextLine();

        System.out.print("Enter Expiry Date (MM/YY): ");
        String expiry = sc.nextLine();

        System.out.print("Enter CVV (3 digits): ");
        String cvv = sc.nextLine();

        // SIMPLE STUDENT-LEVEL VALIDATION
        if (name.isEmpty()) {
            System.out.println("Invalid name.");
            return false;
        }

        if (cardNumber.length() != 16 || !cardNumber.matches("[0-9]+")) {
            System.out.println("Invalid card number.");
            return false;
        }

        if (!expiry.matches("[0-9]{2}/[0-9]{2}")) {
            System.out.println("Invalid expiry date.");
            return false;
        }

        if (cvv.length() != 3 || !cvv.matches("[0-9]+")) {
            System.out.println("Invalid CVV.");
            return false;
        }

        // PAYMENT SUCCESS
        System.out.println("\nProcessing payment...");
        try { Thread.sleep(2000); } catch (Exception e) {}

        System.out.println("Payment Successful! Thank you.");
        return true;
    }
}
