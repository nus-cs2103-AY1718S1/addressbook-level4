//@@author qihao27
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */
public class DeleteByNameCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        DeleteByNameCommand deleteFirstCommand = new DeleteByNameCommand(firstPredicate);
        DeleteByNameCommand deleteSecondCommand = new DeleteByNameCommand(secondPredicate);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByNameCommand deleteFirstCommandCopy = new DeleteByNameCommand(firstPredicate);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_validInput_success() throws Exception {
        DeleteByNameCommand deleteByNameCommand = prepareCommand("carl");
        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, CARL);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(CARL);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multiplePersonsFound_throwCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND);

        DeleteByNameCommand command = prepareCommand("Meier");
        CommandResult commandResult = command.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND, expectedModel);
        assertCommandExecuteSuccess(commandResult, DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND,
            Arrays.asList(DANIEL, BENSON, HOON), Arrays.asList(DANIEL, BENSON, HOON));
    }

    @Test
    public void execute_nameNotFound_throwCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT);

        DeleteByNameCommand command = prepareCommand("John");
        CommandResult commandResult = command.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT, expectedModel);
        assertCommandExecuteSuccess(commandResult, DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT,
            Arrays.asList(DANIEL, BENSON, HOON), Arrays.asList());
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code name}.
     */
    private DeleteByNameCommand prepareCommand(String userInput) {
        DeleteByNameCommand deleteByNameCommand =
                new DeleteByNameCommand(
                new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same
     * if the size of {@code ActualList<ReadOnlyPerson>} is more than 1
     */
    private void assertCommandExecuteSuccess(CommandResult commandResult, String expectedMessage,
                                      List<ReadOnlyPerson> oldList,
                                      List<ReadOnlyPerson> newList) throws CommandException {
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(oldList, model.getFilteredPersonList());
        if (newList.size() > 1) {
            AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
            assertEquals(expectedAddressBook, model.getAddressBook());
        }
    }
}
