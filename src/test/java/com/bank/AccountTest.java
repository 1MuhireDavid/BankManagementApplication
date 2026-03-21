package com.bank;

import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.services.AccountService;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private AccountService manager;
    private Customer regularCustomer;
    private final IdGenerator accountGen = new IdGenerator("ACC");
    private final IdGenerator customerGen = new IdGenerator("CUS");

    @BeforeEach
    void setUp() {
        manager = new AccountService();
        regularCustomer = new RegularCustomer("John Doe", 30, "123", "Address", customerGen);
    }

    @Test
    void testFindAccountExists() {
        // manager.seedData() adds some accounts at instantiation
        assertNotNull(manager.findAccount("ACC001"));
        assertNull(manager.findAccount("ACC999"));
    }

    @Test
    void testProcessTransactionDeposit() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, accountGen);
        assertTrue(acc.processTransaction(500, "Deposit"));
        assertEquals(1500, acc.getBalance());
    }

    @Test
    void testProcessTransactionWithdrawal() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, accountGen);
        assertTrue(acc.processTransaction(500, "Withdrawal"));
        assertEquals(500, acc.getBalance());
    }
}
