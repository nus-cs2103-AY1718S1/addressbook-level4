//@@author majunting
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class DeleteTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String firstArg = "first";
        String secondArg = "second";
        String thirdArg = "first";

        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(firstArg);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(secondArg);
        DeleteTagCommand deleteThirdCommand = new DeleteTagCommand(thirdArg);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(firstArg);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different predicate -> returns false
        assertFalse(deleteThirdCommand.equals(deleteSecondCommand));
    }
}
