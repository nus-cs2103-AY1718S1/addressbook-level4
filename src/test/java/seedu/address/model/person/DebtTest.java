package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Debt.MESSAGE_DEBT_MAXIMUM;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author lawwman
public class DebtTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void debtMaximumValue() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_DEBT_MAXIMUM);
        // value more than Double.MAX_VALUE
        assertFalse(Debt.isValidDebt("100000000000000000000000000000000000000000000000000000000000000000000000"
                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000000000000000000000"));
    }


    @Test
    public void isValidDebt() {
        // invalid debt
        try {
            assertFalse(Debt.isValidDebt("")); // empty string
            assertFalse(Debt.isValidDebt(" ")); // spaces only
            assertFalse(Debt.isValidDebt("phone")); // non-numeric
            assertFalse(Debt.isValidDebt("9011p041")); // alphabets within digits
            assertFalse(Debt.isValidDebt("9312 1534")); // spaces within digits
            assertFalse(Debt.isValidDebt("123.345")); // more than two decimal places
            assertFalse(Debt.isValidDebt("123.3")); // only one decimal places
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }

        // valid debts
        try {
            assertTrue(Debt.isValidDebt("9")); // exactly 1 number
            assertTrue(Debt.isValidDebt("124293842033123")); // huge debts
            assertTrue(Debt.isValidDebt("124293842033123.10")); // two decimal places
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }
}
