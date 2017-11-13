//@@author conantteo
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Test;

public class VcardTest {

    private final String vCardBirthdayRegex = "\\b\\d{4}\\b\\-\\b\\d{2}\\b\\-\\b\\d{2}\\b";

    @Test
    public void valid_buildBirthdayString() {
        // invalid birthday string built
        assertFalse("".matches(vCardBirthdayRegex)); // empty string
        assertFalse(" ".matches(vCardBirthdayRegex)); // spaces only
        assertFalse("03071990".matches(vCardBirthdayRegex)); // only numeric characters
        assertFalse("03/07/1990".matches(vCardBirthdayRegex)); // contains invalid characters.
        assertFalse("03-07-1990".matches(vCardBirthdayRegex)); // wrong DD-MM-YYYY format
        assertFalse("03-07-199000".matches(vCardBirthdayRegex)); // contains invalid number of digits

        // valid birthday string
        assertTrue("1990-07-03".matches(vCardBirthdayRegex)); // YYYY-MM-DD format
    }

    @Test
    public void equals() {
        // same values -> return true
        Vcard uniqueVcard = createVcard(ALICE);
        Vcard uniqueVcardCopy = createVcard(ALICE);
        assertTrue(uniqueVcard.equals(uniqueVcardCopy));

        // same object -> return true
        assertTrue(uniqueVcard.equals(uniqueVcard));

        // null -> returns false
        assertFalse(uniqueVcard.equals(null));

        // different types -> returns false
        assertFalse(uniqueVcard.equals(5));

        // different person -> return false
        Vcard anotherUnqiueVcard = createVcard(BENSON);
        assertFalse(uniqueVcard.equals(anotherUnqiueVcard));
    }

    /**
     * Creates a new Vcard for a {@code person} using the person's details.
     * @param person is a valid person.
     * @return the a Vcard for a person.
     */
    private Vcard createVcard(ReadOnlyPerson person) {
        Vcard vCard = new Vcard(person);
        return vCard;
    }
}
