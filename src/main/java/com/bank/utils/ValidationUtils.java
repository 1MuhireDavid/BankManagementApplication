package com.bank.utils;

import com.bank.models.Account;
import com.bank.services.AccountService;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Utility class providing common validation and input reading routines.
 * Centralises all regex validation for account numbers, emails, and phone numbers.
 */
public class ValidationUtils {

    static Scanner input = new Scanner(System.in);



    private static final String ACCOUNT_NUMBER_REGEX = "^ACC\\d{3}$";
    private static final String EMAIL_REGEX          = "^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX          = "^07\\d{8}$";   // Rwandan format: 07XXXXXXXX



    public static final Predicate<String> IS_VALID_ACCOUNT = s -> s != null && s.matches(ACCOUNT_NUMBER_REGEX);
    public static final Predicate<String> IS_VALID_EMAIL   = s -> s != null && s.matches(EMAIL_REGEX);
    public static final Predicate<String> IS_VALID_PHONE   = s -> s != null && s.matches(PHONE_REGEX);



    public static boolean isValidAccountNumber(String value) {
        return IS_VALID_ACCOUNT.test(value);
    }

    public static boolean isValidEmail(String value) {
        return IS_VALID_EMAIL.test(value);
    }

    public static boolean isValidPhone(String value) {
        return IS_VALID_PHONE.test(value);
    }


    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }

    public static int readTypeNum() {
        while (true) {
            System.out.print("Select type (1-2): ");
            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                if (choice >= 1 && choice <= 2) return choice;
                System.out.println("❌ Invalid selection. Please enter 1 or 2.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }

    public static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val < 0) {
                    System.out.println("❌ Amount cannot be negative.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("❌ Input cannot be empty. Please try again.");
        }
    }

    public static String readCustomerName() {
        while (true) {
            System.out.print("Enter customer name: ");
            String value = input.nextLine().trim();
            if (!value.isEmpty() && value.matches("[a-zA-Z ]+")) return value;
            System.out.println("❌ Invalid name. Only letters and spaces are allowed.");
        }
    }

    /**
     * Reads and validates a contact — accepts email or phone number.
     * Loops until the format matches either pattern.
     */
    public static String readContact(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (IS_VALID_PHONE.test(value)) return value;
            System.out.println("❌ Invalid contact. Enter a valid phone (07XXXXXXXX)");
        }
    }

    /**
     * Reads and validates a contact — accepts email or phone number.
     * Loops until the format matches either pattern.
     */
    public static String readEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (IS_VALID_EMAIL.test(value) ) {
                System.out.println("Email accepted!");
                return value;
            }
            System.out.println("❌ Invalid email format. Please enter a valid address (e.g. name@example.com).");
        }
    }

    /**
     * Reads and validates an account number against ACC\d{3} format.
     */
    public static Account readAccountNumber(AccountService manager) {
        while (true) {
            System.out.print("Enter Account Number: ");
            String accNumber = input.nextLine().trim().toUpperCase();
            if (!IS_VALID_ACCOUNT.test(accNumber)) {
                System.out.println("❌ Invalid format. Account numbers must match ACC followed by 3 digits (e.g. ACC001).");
                continue;
            }
            Account acc = manager.findAccount(accNumber);
            if (acc == null) {
                System.out.println("❌ Account not found. Please try again.");
                continue;
            }
            return acc;
        }
    }

    /**
     * Generic validated string reader — accepts any Predicate<String> rule and custom error message.
     */
    public static String readValidated(String prompt, Predicate<String> rule, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (rule.test(value)) return value;
            System.out.println("❌ " + errorMsg);
        }
    }
}