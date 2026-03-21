package com.bank;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidAmountException;
import com.bank.exception.OverdraftExceededException;
import com.bank.models.CheckingAccount;
import com.bank.models.Customer;
import com.bank.models.RegularCustomer;
import com.bank.models.SavingsAccount;
import com.bank.services.AccountService;
import com.bank.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {

    private AccountService manager;
    private Customer regularCustomer;
    private IdGenerator genAccount;

    @BeforeEach
    void setUp() {
        manager = new AccountService();
        genAccount = new IdGenerator("ACC");
        IdGenerator genCustomer = new IdGenerator("CUS");
        regularCustomer = new RegularCustomer("John Doe", 30, "123", "Address", genCustomer);
    }

    @Test
    void testInsufficientFundsException() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 500, genAccount);

        InsufficientFundsException ex = assertThrows(
                InsufficientFundsException.class,
                () -> acc.withdraw(1000)
        );

        assertEquals("❌Error: Insufficient funds.", ex.getMessage());
    }

    @Test
    void testInvalidAmountException() {
        SavingsAccount acc = new SavingsAccount(regularCustomer, 500, genAccount);

        assertThrows(
                InvalidAmountException.class,
                () -> acc.deposit(-100)
        );
    }

    @Test
    void testOverdraftExceededException() {
        CheckingAccount acc = new CheckingAccount(regularCustomer, 500, genAccount);

        assertThrows(
                OverdraftExceededException.class,
                () -> acc.withdraw(2000)
        );
    }

    @Test
    void testInvalidAccountException() {
        InvalidAccountException ex = new InvalidAccountException("❌Error: Not found");

        assertEquals("❌Error: Not found", ex.getMessage());
    }
}