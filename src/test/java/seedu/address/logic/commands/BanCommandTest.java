package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
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

//@@author jaivigneshvenugopal
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code BanCommand}.
 */
public class BanCommandTest extends CommandTest {

    @Test
    public void execute_banPersonTwice_success() {
        try {
            ReadOnlyPerson personToBan = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(BanCommand.MESSAGE_BAN_BLACKLISTED_PERSON_FAILURE, personToBan.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.addBlacklistedPerson(personToBan);

            prepareCommand(INDEX_FIRST_PERSON).execute();
            BanCommand banCommand = prepareCommand(INDEX_FIRST_PERSON);

            assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        try {
            ReadOnlyPerson personToBan = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            BanCommand banCommand = prepareCommand(INDEX_FIRST_PERSON);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(BanCommand.MESSAGE_BAN_PERSON_SUCCESS, personToBan.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.addBlacklistedPerson(personToBan);

            assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
            assertTrue(personToBan.getAsText().equals(model.getFilteredPersonList()
                    .get(INDEX_FIRST_PERSON.getZeroBased()).getAsText()));
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        prepareCommand(outOfBoundIndex);
        fail(UNEXPECTED_EXECTION);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        prepareCommand(outOfBoundIndex);
        fail(UNEXPECTED_EXECTION);
    }

    //@@author khooroko
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
            ReadOnlyPerson personToBan = model.getSelectedPerson();
            BanCommand banCommand = prepareCommand();

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(BanCommand.MESSAGE_BAN_PERSON_SUCCESS, personToBan.getName());

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.addBlacklistedPerson(personToBan);

            assertCommandSuccess(banCommand, model, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        BanCommand banCommand = prepareCommand();
        fail(UNEXPECTED_EXECTION);
    }

    //@@author jaivigneshvenugopal
    @Test
    public void equals() throws Exception {
        BanCommand banFirstCommand = new BanCommand(INDEX_FIRST_PERSON);
        BanCommand banSecondCommand = new BanCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(banFirstCommand.equals(banFirstCommand));

        // same values -> returns true
        BanCommand banFirstCommandCopy = new BanCommand(INDEX_FIRST_PERSON);
        assertTrue(banFirstCommand.equals(banFirstCommandCopy));

        // different types -> returns false
        assertFalse(banFirstCommand.equals(1));

        // null -> returns false
        assertFalse(banFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(banFirstCommand.equals(banSecondCommand));
    }

    /**
     * Returns a {@code BanCommand} with the parameter {@code index}.
     */
    private BanCommand prepareCommand(Index index) throws CommandException {
        BanCommand banCommand = new BanCommand(index);
        banCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return banCommand;
    }

    /**
     * Returns a {@code BanCommand} with no parameters.
     */
    private BanCommand prepareCommand() throws CommandException {
        BanCommand banCommand = new BanCommand();
        banCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return banCommand;
    }

}
