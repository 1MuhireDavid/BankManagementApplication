package com.bank;

import com.bank.models.*;
import com.bank.services.FilePersistenceService;
import com.bank.utils.IdGenerator;
import java.util.*;

public class FileSavePolishingTest {
    public static void main(String[] args) {
        IdGenerator accountGen = new IdGenerator("ACC");
        IdGenerator customerGen = new IdGenerator("CUS");
        
        Customer customer = new RegularCustomer("John Doe", 30, "123456789", "john@example.com", "123 Main St", customerGen);
        Account account = new CheckingAccount(customer, 1000.0, accountGen);
        
        List<Account> accounts = List.of(account);
        List<Transaction> transactions = new ArrayList<>();
        
        FilePersistenceService persistenceService = new FilePersistenceService();
        
        System.out.println("Starting polished save test...");
        persistenceService.saveAll(accounts, transactions);
        System.out.println("Polished save test completed.");
    }
}
