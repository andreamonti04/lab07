package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private final static int FIRST_DEPOSIT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), FIRST_DEPOSIT);
        assertEquals(FIRST_DEPOSIT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(FIRST_DEPOSIT - TRANSACTION_FEE - MANAGEMENT_FEE, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            bankAccount.withdraw(mRossi.getUserID(), -FIRST_DEPOSIT);
        } catch(IllegalArgumentException e){
            assertNotNull(e.getMessage());  //Non-null message
            assertFalse(e.getMessage().isBlank()); //Not a blank or empty message
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(mRossi.getUserID(), FIRST_DEPOSIT);
        } catch(IllegalArgumentException e){
            assertNotNull(e.getMessage()); //Non-null message
            assertFalse(e.getMessage().isBlank()); //Not a blank or empty message
        }
    }
}
