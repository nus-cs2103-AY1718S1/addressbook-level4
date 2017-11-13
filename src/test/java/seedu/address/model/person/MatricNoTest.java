package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author April0616
public class MatricNoTest {

    @Test
    public void isValidMatricNo() {
        // invalid MatricNo numbers
        assertFalse(MatricNo.isValidMatricNo(" ")); // spaces only
        assertFalse(MatricNo.isValidMatricNo("A016253K")); // not exactly 9-digit
        assertFalse(MatricNo.isValidMatricNo("40162533J")); // not start with 'A' but a number
        assertFalse(MatricNo.isValidMatricNo("B0162533J")); // not start with 'A' but a letter
        assertFalse(MatricNo.isValidMatricNo("A90p3041F")); // alphabets within 2nd-8th digits
        assertFalse(MatricNo.isValidMatricNo("A014 2333H")); // spaces within digits
        assertFalse(MatricNo.isValidMatricNo("A01423332")); // not end with a letter

        // valid MatricNo numbers/ empty when optional
        assertTrue(MatricNo.isValidMatricNo("")); // empty string
        assertTrue(MatricNo.isValidMatricNo("A0172631H"));
        assertTrue(MatricNo.isValidMatricNo("A0112331K"));
        assertTrue(MatricNo.isValidMatricNo("a0172631h")); //case-insensitive
        assertTrue(MatricNo.isValidMatricNo("A0172631h")); //case-insensitive
        assertTrue(MatricNo.isValidMatricNo("a0172631H")); //case-insensitive
    }
}
