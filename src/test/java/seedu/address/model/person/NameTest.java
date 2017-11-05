package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
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

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    //@@author DarrenCzen
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check name field value
        Name nameX = new Name("Capital Tan");
        Name nameY = new Name("Capital Tan");
        assertTrue(nameX.equals(nameY) && nameY.equals(nameX));
        assertTrue(nameX.hashCode() == nameY.hashCode());
    }

    @Test
    public void testCapitaliseMethod() throws IllegalValueException {
        String name = "peter jack";
        String expectedName = "Peter Jack";
        assertTrue(Name.isValidName(name)); // alphabets only

        String newName = Name.toCapitalized(name);
        assertEquals(newName.toString(), expectedName.toString());
    }
    //@@author
}
