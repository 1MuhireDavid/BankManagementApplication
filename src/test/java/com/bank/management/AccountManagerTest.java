package com.bank.management;

import com.bank.account.Account;
import com.bank.account.SavingsAccount;
import com.bank.customer.PremiumCustomer;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountManagerTest {
    private AccountManager accountManager;
    private Account account;

    @Before
    public void setUp() {
        accountManager = new AccountManager();
    }
    @Test
    public void testAddAccount() {
        SavingsAccount savingsAccount = new SavingsAccount(new PremiumCustomer("muhire",20,"07882812","rwanda kigali"),5000);
        assertNotNull(savingsAccount.getAccountNumber());
        assertEquals("ACC001", savingsAccount.getAccountNumber());

    }

    public void testFindAccount() {
    }

    public void testViewAllAccounts() {
    }

    public void testGetTotalBalance() {
    }

    public void testGetAccountCount() {
    }
}