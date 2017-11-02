//@@author majunting
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        int firstArg = 1;
        int secondArg = 2;

        SortCommand firstCommand = new SortCommand(firstArg);
        SortCommand secondCommand = new SortCommand(secondArg);

        assertTrue(firstCommand.equals(firstCommand));

        SortCommand firstCommandCopy = new SortCommand(firstArg);
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }
}
