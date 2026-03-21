package com.bank;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.OverdraftExceededException;
import com.bank.models.CheckingAccount;
import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Customer regularCustomer;
    private IdGenerator genAccount;

    @BeforeEach
    void setUp() {
        genAccount = new IdGenerator("ACC");
        IdGenerator genCustomer = new IdGenerator("CUS");
        regularCustomer = new RegularCustomer("John Doe", 30, "123", "Address", genCustomer);
    }

    @Test
    void testSavingsDepositSuccess() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        acc.deposit(500);
        assertEquals(1500, acc.getBalance(), "Balance should increase by deposit amount.");
    }

    @Test
    void testSavingsDepositInvalidAmount() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        assertThrows(InvalidAmountException.class, () -> acc.deposit(-50), "Negative deposit should throw exception.");
        assertThrows(InvalidAmountException.class, () -> acc.deposit(0), "Zero deposit should throw exception.");
    }

    @Test
    void testSavingsWithdrawSuccess() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        acc.withdraw(400); // leaves 600, which is >= min balance of 500
        assertEquals(600, acc.getBalance(), "Balance should decrease by withdrawal amount.");
    }

    @Test
    void testSavingsWithdrawInvalidAmount() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 1000, genAccount);
        assertThrows(InvalidAmountException.class, () -> acc.withdraw(-100), "Negative withdrawal should throw exception.");
    }

    @Test
    void testSavingsWithdrawInsufficientFunds() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 500, genAccount);
        // Withdraw 600 from 500 balance
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(600), "Withdrawal exceeding balance should throw exception.");
        // Withdraw 100 from 500 balance (leaves 400 < 500 min balance)
        assertThrows(InsufficientFundsException.class, () -> acc.withdraw(100), "Withdrawal breaching minimum balance should throw exception.");
    }

    @Test
    void testCheckingDepositSuccess() {
        CheckingAccount acc = new CheckingAccount(regularCustomer, 500, genAccount);
        acc.deposit(200);
        assertEquals(700, acc.getBalance());
    }

    @Test
    void testCheckingWithdrawSuccess() {
        CheckingAccount acc = new CheckingAccount(regularCustomer, 500, genAccount);
        acc.withdraw(1000); // Leaves -500, which is within overdraft limit (default 1000)
        assertEquals(-500, acc.getBalance());
    }

    @Test
    void testCheckingWithdrawOverdraftExceeded() {
        CheckingAccount acc = new CheckingAccount(regularCustomer, 500, genAccount);
        assertThrows(OverdraftExceededException.class, () -> acc.withdraw(2000), "Withdrawal exceeding overdraft limit should throw exception.");
    }
    @Test
    void testTransferSuccess() {
        SavingsAccount source = new SavingsAccount(regularCustomer, 2000, genAccount);
        CheckingAccount target = new CheckingAccount(regularCustomer, 500, genAccount);

        source.transfer(target, 500);

        assertEquals(1500, source.getBalance(), "Source account balance should decrease.");
        assertEquals(1000, target.getBalance(), "Target account balance should increase.");
    }

    @Test
    void testTransferInvalidAmount() {
        SavingsAccount source = new SavingsAccount(regularCustomer, 2000, genAccount);
        CheckingAccount target = new CheckingAccount(regularCustomer, 500, genAccount);

        assertThrows(InvalidAmountException.class, () -> source.transfer(target, -200));
        assertEquals(2000, source.getBalance(), "Source balance should remain unchanged.");
        assertEquals(500, target.getBalance(), "Target balance should remain unchanged.");
    }

    @Test
    void testTransferInsufficientFunds() {
        SavingsAccount source = new SavingsAccount(regularCustomer, 600, genAccount); // 500 is min balance
        CheckingAccount target = new CheckingAccount(regularCustomer, 500, genAccount);

        assertThrows(InsufficientFundsException.class, () -> source.transfer(target, 200), "Transfer exceeding funds should fail.");
        assertEquals(600, source.getBalance(), "Source balance should remain unchanged.");
        assertEquals(500, target.getBalance(), "Target balance should remain unchanged.");
    }
    
    @Test
    void testTransferToNullTarget() {
        SavingsAccount source = new SavingsAccount(regularCustomer, 2000, genAccount);
        assertThrows(IllegalArgumentException.class, () -> source.transfer(null, 500), "Transfer to null target should throw exception.");
    }
}