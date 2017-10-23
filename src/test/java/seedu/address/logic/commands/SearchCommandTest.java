package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */

public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SearchContainsKeywordsPredicate firstPredicate =
                new SearchContainsKeywordsPredicate(Collections.singletonList("first"));

        SearchContainsKeywordsPredicate secondPredicate =
                new SearchContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

    }
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchCommand command = prepareCommand("Kurz 13.10.1997");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));
    }


    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        SearchCommand command =
                new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SearchCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
