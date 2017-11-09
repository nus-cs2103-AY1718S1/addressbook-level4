package seedu.address.logic.commands.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author dennaloh
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code GMapCommand}.
 */
public class GMapCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        GMapCommand gMapFirstCommand = new GMapCommand(INDEX_FIRST_PERSON);
        GMapCommand gMapSecondCommand = new GMapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(gMapFirstCommand.equals(gMapFirstCommand));

        // same values -> returns true
        GMapCommand gMapFirstCommandCopy = new GMapCommand(INDEX_FIRST_PERSON);
        assertTrue(gMapFirstCommand.equals(gMapFirstCommandCopy));

        // different types -> returns false
        assertFalse(gMapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gMapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(gMapFirstCommand.equals(gMapSecondCommand));
    }
}
