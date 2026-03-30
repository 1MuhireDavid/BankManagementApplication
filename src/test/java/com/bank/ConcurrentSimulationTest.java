package com.bank;

import com.bank.concurrent.ConcurrentTransactionSimulator;
import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.services.AccountService;
import com.bank.services.TransactionService;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentSimulationTest {

    @Test
    public void testRandomizedSimulation() {
        AccountService accountService = new AccountService();
        TransactionService transactionService = new TransactionService();
        IdGenerator idGen = new IdGenerator("ACC");
        IdGenerator custGen = new IdGenerator("CUS");
        IdGenerator txGen = new IdGenerator("TXN");

        Customer customer = new RegularCustomer("Test User", 30, "123", "test@test.com", "Address", custGen);
        Account account = new SavingsAccount(customer, 1000.0, idGen);
        accountService.addAccount(account);

        ConcurrentTransactionSimulator simulator = new ConcurrentTransactionSimulator(accountService, transactionService, txGen);

        double baseAmount = 100.0;
        int threads = 5;

        // Run randomized simulation
        simulator.run(account.getAccountNumber(), threads, baseAmount, true);

        // Verify that we have transactions recorded
        assertTrue(transactionService.getTransactionCount() > 0);

        // Verify that at least some transactions have different amounts (very likely with 10 txns)
        long distinctAmounts = transactionService.getTransactions().stream()
                .filter(t -> t.getAccountNumber().equals(account.getAccountNumber()))
                .map(t -> t.getAmount())
                .distinct()
                .count();

        System.out.println("Distinct transaction amounts: " + distinctAmounts);
        assertTrue(distinctAmounts > 1, "Expected more than one distinct amount in randomized simulation");
    }
}
