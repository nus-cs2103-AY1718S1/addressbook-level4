package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private int firstValue;
    private int secondValue;
    private int thirdValue;

    @Before
    public void setUp() {
        firstValue = 1;
        secondValue = 2;
        thirdValue = 3;
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    // Interaction with model
    @Test
    public void execute_listIsSorted_showsEverything() {
        SortCommand command = prepareCommand(firstValue);
        assertCommandSuccess(command, model, SortCommand.MESSAGE_SUCCESS[firstValue - 1], expectedModel);
    }

    @Test
    public void execute_nameValue_listSorted() {
        SortCommand command = prepareCommand(firstValue);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS[firstValue - 1], Arrays.asList(ALICE, BENSON, CARL,
                DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_tagValue_listSorted() {
        SortCommand command = prepareCommand(secondValue);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS[secondValue - 1], Arrays.asList(ALICE, CARL, DANIEL,
                ELLE, FIONA, GEORGE, BENSON));
    }

    @Test
    public void execute_addValue_listSort() {
        SortCommand command = prepareCommand(thirdValue);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS[thirdValue - 1], Arrays.asList(DANIEL, ALICE, BENSON,
                GEORGE, FIONA, ELLE, CARL));
    }

    @Test
    public void equals() {
        final SortCommand userCommand = new SortCommand(firstValue);

        // same object -> returns true
        assertTrue(userCommand.equals(userCommand));

        // same values -> returns equals
        SortCommand firstSortCommand = new SortCommand(1);
        assertTrue(userCommand.equals(firstSortCommand));

        // different types -> returns false
        assertFalse(userCommand.equals(new ListCommand()));

        // null -> returns false
        assertFalse(userCommand.equals(null));

        // different command -> returns false
        assertFalse(userCommand.equals(new SortCommand(2)));
    }

    private SortCommand prepareCommand(int type) {
        SortCommand sortCommand = new SortCommand(type);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertSortSuccess(SortCommand command, String expectedMessage, List<ReadOnlyPerson>
            expectedList) {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
