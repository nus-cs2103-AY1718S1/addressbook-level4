package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.NON_COMPULSORY_BLOODTYPE;

import org.junit.Test;

//@@author Ernest
public class BloodtypeTest {

    @Test
    public void isValidBloodType() {
        // invalid blood types
        assertFalse(Bloodtype.isValidBloodType("")); // empty string
        assertFalse(Bloodtype.isValidBloodType(" ")); // spaces only
        assertFalse(Bloodtype.isValidBloodType("L")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("cat")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("$%")); // not one of twelve valid inputs
        assertFalse(Bloodtype.isValidBloodType("ABCDE")); // more than three characters
        assertFalse(Bloodtype.isValidBloodType("+")); // "+" or "-" alone
        assertFalse(Bloodtype.isValidBloodType("-")); // "+" or "-" alone
        assertFalse(Bloodtype.isValidBloodType("B++"));
        assertFalse(Bloodtype.isValidBloodType("+B"));
        assertFalse(Bloodtype.isValidBloodType("+F"));
        assertFalse(Bloodtype.isValidBloodType("N"));
        assertFalse(Bloodtype.isValidBloodType("?"));
        assertFalse(Bloodtype.isValidBloodType("BB"));
        assertFalse(Bloodtype.isValidBloodType("BA"));
        assertFalse(Bloodtype.isValidBloodType("A B"));
        assertFalse(Bloodtype.isValidBloodType("A +"));
        assertFalse(Bloodtype.isValidBloodType("A+ "));
        assertFalse(Bloodtype.isValidBloodType(" A+"));

        // valid blood types
        assertTrue(Bloodtype.isValidBloodType("AB")); // all caps
        assertTrue(Bloodtype.isValidBloodType("ab")); // all small letters
        assertTrue(Bloodtype.isValidBloodType("aB")); // case insensitive
        assertTrue(Bloodtype.isValidBloodType("O")); // one character
        assertTrue(Bloodtype.isValidBloodType("B+")); // inclusive of + or - symbol

        //Placeholder blood type if no input detected
        assertTrue(Bloodtype.isValidBloodType(NON_COMPULSORY_BLOODTYPE));

        //Potential weird cases which could be improved on in the future
        assertTrue(Bloodtype.isValidBloodType(NON_COMPULSORY_BLOODTYPE + "+"));
        assertTrue(Bloodtype.isValidBloodType("O+"));
        assertTrue(Bloodtype.isValidBloodType("AB+"));
        assertTrue(Bloodtype.isValidBloodType("ab+"));
        assertTrue(Bloodtype.isValidBloodType("aB+"));


    }
}
