//@@author inGall
package seedu.address.logic.commands;

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

public class SortAgeCommandTest {

    public static final int FIRST_PERSON = 0;
    public static final int SECOND_PERSON = 1;
    public static final int THIRD_PERSON = 2;

    private Model model;
    private Model expectedModel;
    private SortAgeCommand sortAgeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortAgeCommand = new SortAgeCommand();
        sortAgeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameFirstPersonBeforeSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(
                expectedModel.getFilteredPersonList().get(FIRST_PERSON)));
    }

    @Test
    public void execute_differentFirstPersonAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        model.addPerson(Adeline);
        assertSortSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Adeline));
    }

    @Test
    public void execute_listWithMultipleBirthdaysAfterSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        Person Jamie = new PersonBuilder().withName("Jamie").withBirthday("08/02/1995").build();
        Person Tom = new PersonBuilder().withName("Tom").withBirthday("08/08/1992").build();
        model.addPerson(Adeline);
        model.addPerson(Jamie);
        model.addPerson(Tom);
        assertSortSuccess(sortAgeCommand, model, SortAgeCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Tom));
        assertTrue(model.getFilteredPersonList().get(SECOND_PERSON).equals(Jamie));
        assertTrue(model.getFilteredPersonList().get(THIRD_PERSON).equals(Adeline));
    }

}
