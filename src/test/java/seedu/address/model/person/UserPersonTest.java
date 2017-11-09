package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalUserPerson.JAMES;
import static seedu.address.testutil.TypicalUserPerson.WILLIAM;
import static seedu.address.testutil.TypicalUserPerson.getTypicalUserPerson;

import org.junit.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author bladerail
public class UserPersonTest {


    private UserPerson userPerson;

    @Test
    public void modifyUserPerson_returnsCorrectUserPerson() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UserPerson());
        userPerson = model.getUserPerson();
        model.updateUserPerson(JAMES);

        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(),
                getTypicalUserPerson());
        assertEquals(model, expectedModel);
    }

    @Test
    public void equals() {
        userPerson = new UserPerson();
        UserPerson expectedUserPerson = getTypicalUserPerson();
        assertFalse(userPerson.equals(expectedUserPerson));

        userPerson = new UserPerson(JAMES);
        assertTrue(userPerson.equals(expectedUserPerson));

        userPerson = new UserPerson(WILLIAM);
        assertFalse(userPerson.equals(expectedUserPerson));
    }
}
