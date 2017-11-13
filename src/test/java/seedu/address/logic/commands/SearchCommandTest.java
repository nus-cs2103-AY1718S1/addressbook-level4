package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.NameWithTagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //@@author JYL123
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

    //@@author JYL123
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = "Unknown tag";
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    //@@author lancehaoh
    @Test
    /**
     * Tests if search command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     */
    public void executeSearchCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltSearchCommand("searcf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltSearchCommand("searf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    //@@author lancehaoh
    /**
     * Helper method to autocorrect wrongly spelt commands
     */
    private Command parseWronglySpeltSearchCommand(String userinput) {
        AddressBookParser parser = new AddressBookParser();
        Command parsedCommand = null;
        // Parse a search command with small spelling error
        try {
            parsedCommand = parser.parseCommand(userinput);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return parsedCommand;
    }

    //@@author JYL123
    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        SearchCommand command =
                new SearchCommand(new NameWithTagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    //@@author JYL123
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

    //@@author lancehaoh
    @After
    /**
     * Clears the autocorrect status messages to prevent them from persisting among
     * tests
     */
    public void clearAutocorrectStatusMessage() {
        AutoCorrectCommand.clearMessageToUser();
    }

}
