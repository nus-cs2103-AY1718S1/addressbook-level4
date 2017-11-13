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

public class SortBirthdayCommandTest {

    public static final int FIRST_PERSON = 0;
    public static final int SECOND_PERSON = 1;

    private Model model;
    private Model expectedModel;
    private SortBirthdayCommand sortBirthdayCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getDatabase(), new UserPrefs());

        sortBirthdayCommand = new SortBirthdayCommand();
        sortBirthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_successful() throws Exception {
        assertCommandSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
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
        assertSortSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Adeline));
    }

    @Test
    public void execute_differentFirstPersonAfterSecondPersonAddedAndSorted() throws Exception {
        Person Adeline = new PersonBuilder().withName("Adeline").withBirthday("05/05/1995").build();
        Person Zoe = new PersonBuilder().withName("Zoe").withBirthday("01/01/1991").build();
        model.addPerson(Adeline);
        model.addPerson(Zoe);
        assertSortSuccess(sortBirthdayCommand, model, SortBirthdayCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.getFilteredPersonList().get(FIRST_PERSON).equals(Zoe));
        assertTrue(model.getFilteredPersonList().get(SECOND_PERSON).equals(Adeline));
    }

}
