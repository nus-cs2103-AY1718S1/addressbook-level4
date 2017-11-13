//@@author tshradheya
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

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
import seedu.address.model.person.PersonContainsTagPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class ViewTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagPredicate firstPredicate =
                new PersonContainsTagPredicate("foo");
        PersonContainsTagPredicate secondPredicate =
                new PersonContainsTagPredicate("bar");

        ViewTagCommand viewTagFirstCommand = new ViewTagCommand(firstPredicate);
        ViewTagCommand viewTagSecondCommand = new ViewTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommand));

        // same values -> returns true
        ViewTagCommand viewTagFirstCommandCopy = new ViewTagCommand(firstPredicate);
        assertTrue(viewTagFirstCommand.equals(viewTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewTagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewTagFirstCommand.equals(viewTagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ViewTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ViewTagCommand command = prepareCommand("friend");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_oneKeyword_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ViewTagCommand command = prepareCommand("enemy");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(ELLE));
    }

    /**
     * Parses {@code keyword} into a {@code ViewTagCommand}.
     */
    private ViewTagCommand prepareCommand(String keyword) {
        ViewTagCommand command =
                new ViewTagCommand(new PersonContainsTagPredicate(keyword));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
