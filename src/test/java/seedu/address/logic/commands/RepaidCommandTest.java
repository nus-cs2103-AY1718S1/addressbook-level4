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
 * Contains integration tests (interaction with the Model) and unit tests for {@code RepaidCommand}.
 */
public class RepaidCommandTest extends CommandTest {

    @Test
    public void execute_repaidPersonTwice_success() throws Exception {
        ReadOnlyPerson repaidPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(RepaidCommand.MESSAGE_REPAID_PERSON_FAILURE, repaidPerson.getName());;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addWhitelistedPerson(repaidPerson);

        prepareCommand(INDEX_FIRST_PERSON).execute();
        RepaidCommand repaidCommand = prepareCommand(INDEX_FIRST_PERSON);

        assertCommandSuccess(repaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson repaidPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RepaidCommand repaidCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(RepaidCommand.MESSAGE_REPAID_PERSON_SUCCESS, repaidPerson.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addWhitelistedPerson(repaidPerson);

        assertCommandSuccess(repaidCommand, model, expectedMessage, expectedModel);
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
        RepaidCommand repaidCommand = prepareCommand(outOfBoundIndex);
        fail(UNEXPECTED_EXECTION);
    }

    //@@author khooroko
    @Test
    public void execute_noIndexPersonSelected_success() throws Exception {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        ReadOnlyPerson personToRepaid = model.getSelectedPerson();
        RepaidCommand repaidCommand = prepareCommand();

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(RepaidCommand.MESSAGE_REPAID_PERSON_SUCCESS, personToRepaid.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addWhitelistedPerson(personToRepaid);

        assertCommandSuccess(repaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand();
        fail(UNEXPECTED_EXECTION);
    }

    //@@author jaivigneshvenugopal
    @Test
    public void equals() {
        try {
            RepaidCommand repaidFirstCommand = new RepaidCommand(INDEX_FIRST_PERSON);
            RepaidCommand repaidSecondCommand = new RepaidCommand(INDEX_SECOND_PERSON);

            // same object -> returns true
            assertTrue(repaidFirstCommand.equals(repaidFirstCommand));

            // same values -> returns true
            RepaidCommand repaidFirstCommandCopy = new RepaidCommand(INDEX_FIRST_PERSON);
            assertTrue(repaidFirstCommand.equals(repaidFirstCommand));

            // different types -> returns false
            assertFalse(repaidFirstCommand.equals(1));

            // null -> returns false
            assertFalse(repaidFirstCommand.equals(null));

            // different person -> returns false
            assertFalse(repaidFirstCommand.equals(repaidSecondCommand));
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    /**
     * Returns a {@code RepaidCommand} with the parameter {@code index}.
     */
    private RepaidCommand prepareCommand(Index index) throws CommandException {
        RepaidCommand repaidCommand = new RepaidCommand(index);
        repaidCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return repaidCommand;
    }

    /**
     * Returns a {@code RepaidCommand} with no parameters.
     */
    private RepaidCommand prepareCommand() throws CommandException {
        RepaidCommand repaidCommand = new RepaidCommand();
        repaidCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return repaidCommand;
    }

}
