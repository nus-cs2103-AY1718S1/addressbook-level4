package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday("07-01-1995");

        //same object -> returns true
        assertTrue(birthday.equals(birthday));

        //same value -> returns true
        Birthday birthdayCopy = new Birthday("07-01-1995");
        assertTrue(birthday.equals(birthdayCopy));

        //different types -> returns false
        assertFalse(birthday.equals(1));

        //null -> returns false
        assertFalse(birthday.equals(null));

        //different person -> returns false
        Birthday differentBirthday = new Birthday("08-01-1995");
        assertFalse(birthday.equals(differentBirthday));
    }
}
