//@@author majunting
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code ListAlphabetCommand}.
 */
public class ListAlphabetCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameStartsWithAlphabetPredicate firstPredicate =
                new NameStartsWithAlphabetPredicate("f");
        NameStartsWithAlphabetPredicate secondPredicate =
                new NameStartsWithAlphabetPredicate("s");

        ListAlphabetCommand listFirstCommand = new ListAlphabetCommand(firstPredicate);
        ListAlphabetCommand listSecondCommand = new ListAlphabetCommand(secondPredicate);

        assertTrue(listFirstCommand.equals(listFirstCommand));

        ListAlphabetCommand listFirstCommandCopy = new ListAlphabetCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        assertFalse(listFirstCommand.equals(1));
        assertFalse(listFirstCommand.equals(null));
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ListAlphabetCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code ListAlphabetCommand}.
     */
    private ListAlphabetCommand prepareCommand(String input) {
        ListAlphabetCommand command =
                new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(input));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListAlphabetCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
