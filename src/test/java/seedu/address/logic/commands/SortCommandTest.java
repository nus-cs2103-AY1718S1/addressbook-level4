//@@author inGall
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertSortSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    public static final int FIRST_PERSON = 0;

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameFirstPersonBeforeSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").build();
        model.addPerson(Adeline);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

    @Test
    public void execute_differentFirstPersonAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").build();
        model.addPerson(Adeline);
        assertSortSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
        assertFalse(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

}
