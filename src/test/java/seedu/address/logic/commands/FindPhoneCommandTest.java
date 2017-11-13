//@@author quangtdn
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
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
import seedu.address.model.person.NameContainsPhonePredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsPhonePredicate firstPredicate =
                new NameContainsPhonePredicate(Collections.singletonList("12345678"));
        NameContainsPhonePredicate secondPredicate =
                new NameContainsPhonePredicate(Collections.singletonList("87654321"));

        FindPhoneCommand findPhoneFirstCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findPhoneSecondCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommand));

        // same values -> returns true
        FindPhoneCommand findPhoneFirstCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommandCopy));

        // different types -> returns false
        assertFalse(findPhoneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findPhoneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findPhoneFirstCommand.equals(findPhoneSecondCommand));
    }

    @Test
    public void execute_noInputNumber_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPhoneCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleNumbers_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindPhoneCommand command = prepareCommand(
                CARL.getPhone().toString() + " " + ELLE.getPhone().toString() + " 1111222");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindPhoneCommand prepareCommand(String userInput) {
        FindPhoneCommand command =
                new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPhoneCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
//@@author
