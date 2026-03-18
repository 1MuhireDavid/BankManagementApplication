package com.bank.utils;

import com.bank.models.Account;
import com.bank.services.AccountManager;

import java.util.Scanner;

/**
 * Utility class providing common validation and input reading routines.
 */
public class ValidationUtils {
    static Scanner input = new Scanner(System.in);
    static private final AccountManager accountManager = new AccountManager();

    /**
     * Validates that a customer name is purely alphabetical and not blank.
     * @param name The name to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidName(String name) {
        return name != null && !name.isBlank() && name.matches("[a-zA-Z ]+");
    }

    /**
     * Validates that an age is within realistic bounds (1-150).
     * @param age The age to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 150;
    }

    /**
     * Validates that an amount is strictly positive.
     * @param amount The amount to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean readAmount(double amount) {
        return amount > 0;
    }

    /**
     * Checks if a string value is not blank.
     * @param value The value to check.
     * @return true if value has printable characters, false otherwise.
     */
    public static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    /**
     * Prompts the user continuously until a valid integer is provided.
     * @param prompt The message to display.
     * @return The read integer.
     */
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    /**
     * Prompts the user continuously until a valid integer is provided.
     * @return The read integer.
     */
    public static int readTypeNum() {
        while (true) {
            System.out.print("Select type (1-2): ");
            try {
                int choice = Integer.parseInt(input.nextLine().trim());

                // Validate the range
                if (choice >= 1 && choice <= 2) {
                    return choice;
                } else {
                    System.out.println("Invalid selection. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user continuously until a valid, non-negative double is provided.
     * @param prompt The message to display.
     * @return The read double.
     */
    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val < 0) {
                    System.out.println("❌Error :Amount cannot be negative.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌Error :Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user continuously until a non-empty string is provided.
     * @param prompt The message to display.
     * @return The read string.
     */
    public static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty())
                return value;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Prompts the user continuously until a valid alphabetic customer name is provided.
     * @return The read proper customer name.
     */
    public static String readCustomerName() {
        while (true) {
            System.out.print("Enter customer name: ");
            String value = input.nextLine().trim();
            if (!value.isEmpty() && value.matches("[a-zA-Z ]+"))
                return value;
            System.out.println("Invalid name. Please enter a valid name with only letters.");
        }
    }

/**
* Prompts the user continuously until a valid alphabetic customer name is provided.
* @return The read proper account number.
* */
    public static Account readAccountNumber() {
        while (true) {
            try {
                String accNumber = readString("Enter Account Number: ").toUpperCase();
                Account acc = accountManager.findAccount(accNumber);
                if (acc == null)
                    throw new com.bank.exception.InvalidAccountException("❌Error: Account not found. Please try again.");
                return acc;
            } catch (com.bank.exception.InvalidAccountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}