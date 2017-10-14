package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DebtTest {

    @Test
    public void isValidDebt() {
        // invalid debt
        assertFalse(Debt.isValidDebt("")); // empty string
        assertFalse(Debt.isValidDebt(" ")); // spaces only
        assertFalse(Debt.isValidDebt("phone")); // non-numeric
        assertFalse(Debt.isValidDebt("9011p041")); // alphabets within digits
        assertFalse(Debt.isValidDebt("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Debt.isValidDebt("9")); // exactly 1 number
        assertTrue(Debt.isValidDebt("124293842033123")); // huge debts
    }
}
