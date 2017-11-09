//@@author qihao27
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
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
    public void execute_findByName_multiplePersonsFound() throws ParseException, CommandException {
        String expectedMessage = "Multiple contacts with specified name found!\n"
            + "Please add more details for distinction or use the following command:\n"
            + DeleteCommand.MESSAGE_USAGE;
        DeleteByNameCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA),
            Arrays.asList());
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code name}.
     */
    private DeleteByNameCommand prepareCommand(String userInput) {
        DeleteByNameCommand deleteByNameCommand =
                new DeleteByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
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
    private void assertCommandSuccess(DeleteByNameCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList,
                                      List<ReadOnlyPerson> actualList) throws CommandException {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        if (actualList.size() > 1) {
            AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
            assertEquals(expectedAddressBook, model.getAddressBook());
        }
    }
}
