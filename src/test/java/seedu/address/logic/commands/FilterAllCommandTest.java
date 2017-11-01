package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
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
import seedu.address.model.person.PersonContainsAllKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;



public class FilterAllCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //@@author adileyzekmoon
    @Test
    public void equals() throws Exception {
        PersonContainsAllKeywordsPredicate firstPredicate =
                new PersonContainsAllKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsAllKeywordsPredicate secondPredicate =
                new PersonContainsAllKeywordsPredicate(Collections.singletonList("second"));

        FilterAllCommand FilterAllFirstCommand = new FilterAllCommand(firstPredicate);
        FilterAllCommand FilterAllSecondCommand = new FilterAllCommand(secondPredicate);

        // same object -> returns true
        assertTrue(FilterAllFirstCommand.equals(FilterAllFirstCommand));

        // same values -> returns true
        FilterAllCommand FilterAllFirstCommandCopy = new FilterAllCommand(firstPredicate);
        assertTrue(FilterAllFirstCommand.equals(FilterAllFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterAllFirstCommand.equals(1));

        // null -> returns false
        assertFalse(FilterAllFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(FilterAllFirstCommand.equals(FilterAllSecondCommand));
    }


    @Test
    public void execute_multipleKeywords_specificPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterAllCommand command = prepareCommand("owesMoney friends");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }
    //@@author

    /**
     * Parses {@code userInput} into a {@code FilterAllCommand}.
     */
    private FilterAllCommand prepareCommand(String userInput) {
        FilterAllCommand command =
                new FilterAllCommand(new PersonContainsAllKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilterAlledList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterAllCommand command, String expMessage, List<ReadOnlyPerson> expList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expMessage, commandResult.feedbackToUser);
        assertEquals(expList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
