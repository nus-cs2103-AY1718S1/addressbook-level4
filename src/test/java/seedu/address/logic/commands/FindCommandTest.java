package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final FindHistory EMPTY_FIND_HISTORY = new FindHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //@@author yewshengkai
    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Collections.singletonList("first"), false);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Collections.singletonList("second"), false);
        //@@author

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroKeywords_noAddressFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_ADDRESS, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroKeywords_noEmailFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_EMAIL, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroKeywords_noHomepageFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_HOMEPAGE, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroKeywords_noPhoneFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_PHONE, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_zeroKeywords_noTagFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_TAG, " ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD, "Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }


    @Test
    public void execute_multipleKeywords_multipleAddressFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_ADDRESS, "wall michegan little");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleKeywords_multipleEmailFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(
                FindCommand.COMMAND_WORD_EMAIL, "heinz@example.com werner@example.com lydia@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleKeywords_multiplePhoneFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(FindCommand.COMMAND_WORD_PHONE, "95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    //@@author yewshengkai
    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String commandPrefix, String userInput) {
        switch (commandPrefix) {

        case FindCommand.COMMAND_WORD:
            FindCommand command = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD, Arrays.asList(userInput.split("\\s+")), false));
            command.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command;
        case FindCommand.COMMAND_WORD_ADDRESS:
            FindCommand command2 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_ADDRESS, Arrays.asList(userInput.split("\\s+")), false));
            command2.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command2;
        case FindCommand.COMMAND_WORD_EMAIL:
            FindCommand command3 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_EMAIL, Arrays.asList(userInput.split("\\s+")), false));
            command3.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command3;
        case FindCommand.COMMAND_WORD_HOMEPAGE:
            FindCommand command4 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_HOMEPAGE, Arrays.asList(userInput.split("\\s+")), false));
            command4.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command4;
        case FindCommand.COMMAND_WORD_PHONE:
            FindCommand command5 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_PHONE, Arrays.asList(userInput.split("\\s+")), false));
            command5.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command5;
        case FindCommand.COMMAND_WORD_TAG:
            FindCommand command6 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_TAG, Arrays.asList(userInput.split("\\s+")), false));
            command6.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command6;

        default:
            break;
        }

        return null;
    }
    //@@author

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
