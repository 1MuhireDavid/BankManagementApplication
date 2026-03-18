package com.bank;

import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.services.AccountManager;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private AccountManager manager;
    private Customer regularCustomer;
    private IdGenerator genAccount;
    private IdGenerator genCustomer;

    @BeforeEach
    void setUp() {
        manager = new AccountManager();
        genAccount = new IdGenerator("ACC");
        genCustomer = new IdGenerator("CUS");
        regularCustomer = new RegularCustomer("John Doe", 30, "123", "Address", genCustomer);
    }

    // --- AccountManager ---

    @Test
    void testFindAccountReturnsAccountWhenExists() {
        // ACC001 is seeded by AccountManager: Kwizera James, SavingsAccount, balance 1500.00
        assertNotNull(manager.findAccount("ACC001"));
    }

    @Test
    void testFindAccountReturnsNullWhenNotFound() {
        assertNull(manager.findAccount("ACC999"));
    }

    // --- SavingsAccount transactions ---

    @Test
    void testDepositIncreasesBalance() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);

        boolean result = acc.processTransaction(500, "Deposit");

        assertTrue(result);
        assertEquals(1500, acc.getBalance());
    }

    @Test
    void testWithdrawalDecreasesBalance() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);

        boolean result = acc.processTransaction(500, "Withdrawal");

        assertTrue(result);
        assertEquals(500, acc.getBalance());
    }
}