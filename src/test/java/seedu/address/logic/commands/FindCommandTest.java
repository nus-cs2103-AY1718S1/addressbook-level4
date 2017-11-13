package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
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
import seedu.address.model.person.PersonContainsBirthdayPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FindCommandTest {
    //@@author tbhbhbh
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand searchFirstCommand = new FindCommand(firstPredicate);
        FindCommand searchSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindCommand command = prepareSearchKeywordsCommand(" ");
        assertCommandSuccess(command, expectedMessage, model.getFilteredPersonList());
    }

    @Test
    public void execute_tagThenName_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("owesMoney Benson");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));

    }

    @Test
    public void execute_nameThenTag_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("Benson owesMoney");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_nameAlternatingUpperAndLowerCases_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("BeNsOn");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_tagAlternatingUpperAndLowerCases_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_initials_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareSearchKeywordsCommand("b");
        assertCommandSuccess(command, expectedMessage, model.getFilteredPersonList());
    }

    //@@author conantteo
    @Test
    public void execute_birthdayMonth_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchBirthdayCommand("05");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));
    }

    /**
     * Parses {@code userInput} which is a birthday month represented by 2 digits into a {@code FindCommand}.
     */
    public FindCommand prepareSearchBirthdayCommand(String userInput) {
        FindCommand command = new FindCommand(new PersonContainsBirthdayPredicate(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    //@@author tbhbhbh
    /**
     * Parses {@code userInput} which are keywords into a {@code FindCommand}.
     */
    public FindCommand prepareSearchKeywordsCommand(String userInput) {
        FindCommand command =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
