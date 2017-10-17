package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.tag.NameWithTagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameWithTagContainsKeywordsPredicate firstPredicate =
                new NameWithTagContainsKeywordsPredicate(Collections.singletonList("friends"));
        NameWithTagContainsKeywordsPredicate secondPredicate =
                new NameWithTagContainsKeywordsPredicate(Collections.singletonList("owesMoney"));

        SearchCommand searchFriendsCommand = new SearchCommand(firstPredicate);
        SearchCommand searchOwesMoneyCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFriendsCommand.equals(searchFriendsCommand));

        // same values -> returns true
        SearchCommand searchFriendsCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(searchFriendsCommand.equals(searchFriendsCommandCopy));

        // different types -> returns false
        assertFalse(searchFriendsCommand.equals(1));

        // null -> returns false
        assertFalse(searchFriendsCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFriendsCommand.equals(searchOwesMoneyCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = "Unknown tag";
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        SearchCommand command =
                new SearchCommand(new NameWithTagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SearchCommand command, String expectedMessage, List<Tag> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonByTagList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
