package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnbanCommand}.
 */
public class UnbanCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUnban = model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnbanCommand unbanCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                + String.format(UnbanCommand.MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeBlacklistedPerson(personToUnban);

        assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnbanCommand unbanFirstCommand = new UnbanCommand(INDEX_FIRST_PERSON);
        UnbanCommand unbanSecondCommand = new UnbanCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unbanFirstCommand.equals(unbanFirstCommand));

        // same values -> returns true
        UnbanCommand unbanFirstCommandCopy = new UnbanCommand(INDEX_FIRST_PERSON);
        assertTrue(unbanFirstCommand.equals(unbanFirstCommandCopy));

        // different types -> returns false
        assertFalse(unbanFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unbanFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unbanFirstCommand.equals(unbanSecondCommand));
    }

    /**
     * Returns a {@code UnbanCommand} with the parameter {@code index}.
     */
    private UnbanCommand prepareCommand(Index index) {
        UnbanCommand unbanCommand = new UnbanCommand(index);
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }

}
