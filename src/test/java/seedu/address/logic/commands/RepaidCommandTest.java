package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RepaidCommand}.
 */
public class RepaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        RepaidCommand repaidCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(repaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RepaidCommand repaidCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(repaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
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
    }

    /**
     * Returns a {@code RepaidCommand} with the parameter {@code index}.
     */
    private RepaidCommand prepareCommand(Index index) {
        RepaidCommand repaidCommand = new RepaidCommand(index);
        repaidCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return repaidCommand;
    }

}
