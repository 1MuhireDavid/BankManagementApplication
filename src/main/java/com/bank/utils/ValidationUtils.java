package com.bank.utils;

import com.bank.exception.InvalidAccountException;
import com.bank.models.Account;
import com.bank.services.AccountService;

import java.util.Scanner;

/**
 * Utility class providing common validation and input reading routines.
 */
public class ValidationUtils {
    static Scanner input = new Scanner(System.in);

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
                    System.out.println("❌Error : Invalid selection. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌Error :Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Prompts the user continuously until a valid, non-negative double is provided.
     * @param prompt The message to display.
     * @return The read double.
     */
    public static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val < 0) {
                    System.out.println("❌Error :Invalid amount. Amount must be greater than 0.");
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
     * Prompts the user continuously until a valid account number is provided.
     * @param manager The AccountManager to search in.
     * @return The read proper account number.
     * */
    public static Account readAccountNumber(AccountService manager) {
        while (true) {
            try {
                String accNumber = readString("Enter Account Number: ").toUpperCase();
                Account acc = manager.findAccount(accNumber);
                if (acc == null)
                    throw new InvalidAccountException("❌Error: Account not found. Please check the account number and try again.");
                return acc;
            } catch (InvalidAccountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}