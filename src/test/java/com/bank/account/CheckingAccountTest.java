package com.bank.account;


import com.bank.customer.Customer;
import com.bank.customer.PremiumCustomer;
import com.bank.customer.RegularCustomer;
import com.bank.management.AccountManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CheckingAccountTest {
    private Account checkingAccount;
    private Account checkingAccount2;
    private Account savingAccount;

    @Before
    public void setUp() {
        Customer premiumCustomer = new PremiumCustomer("David", 12, "0783281113", "Kigali - Rwanda");
        Customer regularCustomer = new RegularCustomer("Kaneza", 30, "078882912", "Kigali - Rwanda");
        checkingAccount = new CheckingAccount(premiumCustomer, 4000);
        checkingAccount2 = new CheckingAccount(regularCustomer,9000);
        savingAccount = new SavingsAccount(premiumCustomer, 3000);

    }
    @Test
    public void testGetAccountType() {
        assertNotNull(checkingAccount.getAccountNumber());
        assertEquals("ACC001", checkingAccount.getAccountNumber());
        assertEquals("Checking",checkingAccount.getAccountType());
    }

    @Test
    public void testSecondAccountNumberToHaveACC2(){
        assertEquals("ACC002",checkingAccount2.getAccountNumber());
    }
    @Test
    public void checkingAccountShouldReturnCheckingType(){
    assertEquals("Checking",checkingAccount.getAccountType());
    }

    @Test
    public void nonCheckingAccountShouldReturnSavingsType(){
        assertEquals("Savings",savingAccount.getAccountType());
    }

    @Test
    public void testDeposit() {
        checkingAccount.deposit(1000);
        assertEquals(5000.00,checkingAccount.getBalance(),0.001);
    }

    @Test
    public void testWithdraw() {
        checkingAccount.withdraw(1000);
        assertEquals(3000.00,checkingAccount.getBalance(),0.001);
    }
}