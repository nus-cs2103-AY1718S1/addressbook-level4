package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnitTest {

    @Test
    public void isValidUnit() {
        // invalid units
        assertFalse(Unit.isValidUnit(""));
        assertFalse(Unit.isValidUnit("#-00"));
        assertFalse(Unit.isValidUnit("#0-"));
        assertFalse(Unit.isValidUnit("#A"));
        assertFalse(Unit.isValidUnit("#1-A"));
        assertFalse(Unit.isValidUnit("#1- 0"));

        // valid units
        assertTrue(Unit.isValidUnit("#03-01"));
        assertTrue(Unit.isValidUnit("#0-0"));
    }
}
