package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names with number
        assertFalse(Name.isValidName("88888888")); // numbers only
        assertFalse(Name.isValidName("peter the 2nd")); // alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr")); // long names
    }

    @Test
    public void nameHashCode() {
        try {
            Name name1 = new Name("peter jack");
            Name name2 = new Name("Capital Tan");

            //hashcode matches for same name --> return true
            assertTrue(name1.hashCode() == name1.hashCode());

            //hashcode don't match for different name --> return false
            assertFalse(name1.hashCode() == name2.hashCode());

        } catch (IllegalValueException e) {
            assert false : "shouldn't hit this case at all";
        }
    }
}
