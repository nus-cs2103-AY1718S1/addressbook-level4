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
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NumberContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FindByNumberTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NumberContainsKeywordsPredicate firstPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("first"));
        NumberContainsKeywordsPredicate secondPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNumberCommand findFirstCommand = new FindNumberCommand(firstPredicate);
        FindNumberCommand findSecondCommand = new FindNumberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindNumberCommand findFirstCommandCopy = new FindNumberCommand(firstPredicate);
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
        FindNumberCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindNumberCommand command = prepareCommand("95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindNumberCommand}.
     */
    private FindNumberCommand prepareCommand(String userInput) {
        FindNumberCommand command =
            new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindNumberCommand command, String eMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(eMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
