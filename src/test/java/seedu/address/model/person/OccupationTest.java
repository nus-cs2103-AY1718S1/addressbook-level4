package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author JavynThun
public class OccupationTest {

    @Test
    public void isValidOccupation() {
        // blank email
        assertFalse(Occupation.isValidOccupation("")); // empty string
        assertFalse(Occupation.isValidOccupation(" ")); // spaces only

        // invalid email
        assertFalse(Occupation.isValidOccupation("@pple, CEO")); // special character in the middle of two strings

        // missing parts
        assertFalse(Occupation.isValidOccupation("Google,Software Engineer")); // missing ' ' after ','
        assertFalse(Occupation.isValidOccupation("Microsoft CEO")); // missing ',' in the middle of two strings
        assertFalse(Occupation.isValidOccupation("Apple")); // missing second part (position)
        assertFalse(Occupation.isValidOccupation("Software Engineer")); // missing first part (company name)

        // valid email
        assertTrue(Occupation.isValidOccupation("Tan Tock Seng Hospital, Nurse"));
        assertTrue(Occupation.isValidOccupation("SMRT, Bus Driver"));
        assertTrue(Occupation.isValidOccupation("NUS, Student"));
    }


}
