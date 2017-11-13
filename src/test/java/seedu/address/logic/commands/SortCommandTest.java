//author huiyiiih
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
    private String name;
    private String tag;
    private String position;
    private String priority;
    private String company;

    @Before
    public void setUp() {
        name = "name";
        tag = "tag";
        position = "position";
        priority = "priority";
        company = "company";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    // Interaction with model
    @Test
    public void execute_listIsSorted_showsEverything() throws Exception {
        SortCommand command = prepareCommand(name);
        assertCommandSuccess(command, model, SortCommand.MESSAGE_SUCCESS + name, expectedModel);
    }

    @Test
    public void execute_nameValue_listSorted() throws Exception {
        SortCommand command = prepareCommand(name);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS + name, Arrays.asList(ALICE, BENSON, CARL,
            DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_tagValue_listSorted() throws Exception {
        SortCommand command = prepareCommand(tag);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS + tag, Arrays.asList(ALICE, CARL, DANIEL,
            ELLE, FIONA, GEORGE, BENSON));
    }

    @Test
    public void execute_positionValue_listSorted() throws Exception {
        SortCommand command = prepareCommand(position);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS + position, Arrays.asList(ALICE, BENSON, GEORGE,
            ELLE, CARL, DANIEL, FIONA));
    }

    @Test
    public void execute_priorityValue_listSorted() throws Exception {
        SortCommand command = prepareCommand(priority);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS + priority, Arrays.asList(BENSON, ELLE,
            ALICE, CARL, DANIEL, FIONA, GEORGE));
    }

    @Test
    public void execute_companyValue_listSorted() throws Exception {
        SortCommand command = prepareCommand(company);
        assertSortSuccess(command, SortCommand.MESSAGE_SUCCESS + company, Arrays.asList(GEORGE, ELLE, DANIEL, ALICE,
            BENSON, CARL, FIONA));
    }






    @Test
    public void equals() {
        final SortCommand userCommand = new SortCommand(name);

        // same object -> returns true
        assertTrue(userCommand.equals(userCommand));

        // same values -> returns equals
        SortCommand firstSortCommand = new SortCommand("name");
        assertTrue(userCommand.equals(firstSortCommand));

        // different types -> returns false
        assertFalse(userCommand.equals(new ListCommand(ListCommand.Option.PERSONS)));

        // null -> returns false
        assertFalse(userCommand.equals(null));

        // different command -> returns false
        assertFalse(userCommand.equals(new SortCommand("tags")));
    }

    private SortCommand prepareCommand(String type) {
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
            expectedList) throws Exception {
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
    }
}
//@@author
