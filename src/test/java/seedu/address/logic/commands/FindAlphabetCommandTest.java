package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ALPHABET_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_NO_ALPHABET_LISTED_OVERVIEW;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsAlphabetsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAlphabetCommand}.
 */
public class FindAlphabetCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsAlphabetsPredicate firstPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("first"));
        NameContainsAlphabetsPredicate secondPredicate =
                new NameContainsAlphabetsPredicate(Collections.singletonList("second"));

        FindAlphabetCommand findFirstCommand = new FindAlphabetCommand(firstPredicate);
        FindAlphabetCommand findSecondCommand = new FindAlphabetCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAlphabetCommand findFirstCommandCopy = new FindAlphabetCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    //Test when no input by user
    @Test
    public void executeZeroAlphabetsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_ALPHABET_LISTED_OVERVIEW);
        FindAlphabetCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }
    //Test when user inputs partial names
    @Test
    public void executeMultipleAlphabetsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 2);
        FindAlphabetCommand command = prepareCommand("Ku");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA));
    }
    //Test when user inputs full names
    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ALPHABET_LISTED_OVERVIEW, 3);
        FindAlphabetCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindAlphabetCommand prepareCommand(String userInput) {
        FindAlphabetCommand command =
                new FindAlphabetCommand(new NameContainsAlphabetsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     */
    private void assertCommandSuccess(FindAlphabetCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());

    }
}
