package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nameSpecified_success() throws Exception {
        String field = "name";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_phoneSpecified_success() throws Exception {
        String field = "phone";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emailSpecified_success() throws Exception {
        String field = "email";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addressSpecified_success() throws Exception {
        String field = "address";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagSpecified_success() throws Exception {
        String field = "tag";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_meetingSpecified_success() throws Exception {
        String field = "meeting";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidField_failure() throws Exception {
        String field = "birthday";
        String expectedMessage = SortCommand.MESSAGE_INVALID_FIELD;
        SortCommand sortCommand = prepareCommand(field);

        assertCommandFailure(sortCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand("TAG");

        // same values -> returns true
        SortCommand commandWithSameValues = new SortCommand("TAG");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different field -> returns false
        assertFalse(standardCommand.equals(new SortCommand("PHONE")));
    }

    private SortCommand prepareCommand(String field) {
        SortCommand sortCommand = new SortCommand(field);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
