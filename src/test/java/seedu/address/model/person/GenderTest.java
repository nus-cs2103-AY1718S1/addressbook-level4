package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author April0616
public class GenderTest {

    @Test
    public void isValidGender() {

        // invalid Gender
        assertFalse(Gender.isValidInput(" ")); // spaces only
        assertFalse(Gender.isValidInput("^")); // only non-alphanumeric characters
        assertFalse(Gender.isValidInput("peter*")); // contains non-alphanumeric characters
        assertFalse(Gender.isValidInput("apple")); // unrelated description
        assertFalse(Gender.isValidInput("fmale")); // wrong input

        // valid Gender
        assertTrue(Gender.isValidInput("")); // empty string when unspecified
        assertTrue(Gender.isValidInput("m"));
        assertTrue(Gender.isValidInput("f"));
        assertTrue(Gender.isValidInput("male"));
        assertTrue(Gender.isValidInput("female"));
        assertTrue(Gender.isValidInput("MALE"));
        assertTrue(Gender.isValidInput("FEMALE"));
        assertTrue(Gender.isValidInput("mAlE"));
        assertTrue(Gender.isValidInput("FemALE"));
        assertTrue(Gender.isValidInput("Male"));
        assertTrue(Gender.isValidInput("Female"));
    }
}
