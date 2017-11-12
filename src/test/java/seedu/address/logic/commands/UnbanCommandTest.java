package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author jaivigneshvenugopal
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnbanCommand}.
 */
public class UnbanCommandTest extends CommandTest {

    @Test
    public void execute_unbanPersonWhoIsNotBlacklisted_failure() throws Exception {
        ReadOnlyPerson personToUnban = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(UnbanCommand.MESSAGE_UNBAN_PERSON_FAILURE, personToUnban.getName());

        UnbanCommand unbanCommand = prepareCommand(INDEX_FIRST_PERSON);

        assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
    }

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

    //@@author khooroko
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            model.updateSelectedPerson(model.getFilteredBlacklistedPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
            ReadOnlyPerson personToUnban = model.getSelectedPerson();
            UnbanCommand unbanCommand = prepareCommand();

            String expectedMessage = ListObserver.BLACKLIST_NAME_DISPLAY_FORMAT
                    + String.format(UnbanCommand.MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.removeBlacklistedPerson(personToUnban);

            assertCommandSuccess(unbanCommand, model, expectedMessage, expectedModel);
        } catch (PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() {
        try {
            UnbanCommand unbanCommand = prepareCommand();
            assertCommandFailure(unbanCommand, model, Messages.MESSAGE_NO_PERSON_SELECTED);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    //@@author jaivigneshvenugopal
    @Test
    public void equals() {
        try {
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
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    /**
     * Returns a {@code UnbanCommand} with the parameter {@code index}.
     */
    private UnbanCommand prepareCommand(Index index) throws CommandException {
        UnbanCommand unbanCommand = new UnbanCommand(index);
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }

    /**
     * Returns a {@code UnbanCommand} with no parameters.
     */
    private UnbanCommand prepareCommand() throws CommandException {
        UnbanCommand unbanCommand = new UnbanCommand();
        unbanCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unbanCommand;
    }

}
