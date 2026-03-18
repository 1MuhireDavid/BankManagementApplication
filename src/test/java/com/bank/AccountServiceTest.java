package com.bank;

import com.bank.models.Account;
import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.services.AccountService;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private AccountService accountService;
    private IdGenerator genAccount;
    private Customer regularCustomer;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
        genAccount = new IdGenerator("T-ACC");
        IdGenerator genCustomer = new IdGenerator("CUS");
        regularCustomer = new RegularCustomer("Test User", 30, "123", "Address", genCustomer);
    }

    @Test
    void testAddAccount() {
        int initialCount = accountService.getAccountCount();
        Account acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        assertTrue(accountService.addAccount(acc));
        assertEquals(initialCount + 1, accountService.getAccountCount());
    }

    @Test
    void testDeleteAccountSuccess() {
        Account acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        accountService.addAccount(acc);
        String accNo = acc.getAccountNumber();
        int countBefore = accountService.getAccountCount();

        assertTrue(accountService.deleteAccount(accNo));
        assertEquals(countBefore - 1, accountService.getAccountCount());
        assertNull(accountService.findAccount(accNo));
    }

    @Test
    void testDeleteAccountNotFound() {
        int countBefore = accountService.getAccountCount();
        assertFalse(accountService.deleteAccount("NON_EXISTENT"));
        assertEquals(countBefore, accountService.getAccountCount());
    }

    @Test
    void testFindAccount() {
        Account acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        accountService.addAccount(acc);
        String accNo = acc.getAccountNumber();
        
        Account found = accountService.findAccount(accNo);
        assertNotNull(found);
        assertEquals(accNo, found.getAccountNumber());
    }
}
