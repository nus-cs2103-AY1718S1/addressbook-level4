package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GenderTest {
    @Test
    public void isValidGender() {
        // invalid Gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("hello world"));
        assertFalse(Gender.isValidGender("1234"));
        assertFalse(Gender.isValidGender("MALEEEEE"));
        assertFalse(Gender.isValidGender("Male123(*)"));

        // valid name
        assertTrue(Gender.isValidGender("FeMaLe")); // alphabets only
        assertTrue(Gender.isValidGender("MALE")); // alphabets only
        assertTrue(Gender.isValidGender("other")); // alphabets only
    }
}

