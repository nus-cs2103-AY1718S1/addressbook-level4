package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author lawwman
public class DebtTest {

    @Test
    public void isValidDebt() {
        // invalid debt
        assertFalse(Debt.isValidDebt("")); // empty string
        assertFalse(Debt.isValidDebt(" ")); // spaces only
        assertFalse(Debt.isValidDebt("phone")); // non-numeric
        assertFalse(Debt.isValidDebt("9011p041")); // alphabets within digits
        assertFalse(Debt.isValidDebt("9312 1534")); // spaces within digits
        assertFalse(Debt.isValidDebt("123.345")); // more than two decimal places
        assertFalse(Debt.isValidDebt("123.3")); // only one decimal places
        // valid debts
        assertTrue(Debt.isValidDebt("9")); // exactly 1 number
        assertTrue(Debt.isValidDebt("124293842033123")); // huge debts
        assertTrue(Debt.isValidDebt("124293842033123.10")); // two decimal places
    }
}
