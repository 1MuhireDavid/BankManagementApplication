package com.bank;

import com.bank.exception.InsufficientFundsException;
import com.bank.exception.InvalidAccountException;
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

public class ExceptionTest {
    private IdGenerator genCustomer;
    private IdGenerator genAccount;

    @BeforeEach
    void setUp() {
        genCustomer = new IdGenerator("CUS");
        genAccount = new IdGenerator("ACC");
    }
    @Test
    void testInsufficientFundsException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr", genCustomer);
        SavingsAccount acc = new SavingsAccount(customer, 500, genAccount);
        
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            acc.withdraw(1000); // 1000 > 500
        });
        
        assertEquals("Insufficient funds.", exception.getMessage());
    }

    @Test
    void testInvalidAmountException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr", genCustomer);
        SavingsAccount acc = new SavingsAccount(customer, 500, genAccount);
        
        assertThrows(InvalidAmountException.class, () -> {
            acc.deposit(-100);
        });
    }

    @Test
    void testOverdraftExceededException() {
        Customer customer = new RegularCustomer("Test", 20, "123", "Addr", genCustomer);
        CheckingAccount acc = new CheckingAccount(customer, 500, genAccount);
        assertThrows(OverdraftExceededException.class, () -> {
            acc.withdraw(2000); // 2000 > 1500
        });
    }

    @Test
    void testInvalidAccountException() {
        InvalidAccountException ex = new InvalidAccountException("Not found");
        assertEquals("Not found", ex.getMessage());
    }
}
