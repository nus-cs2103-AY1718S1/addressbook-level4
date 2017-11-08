package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author freesoup
public class SortCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void sort_validComparator_success() {
        //valid sort for phone descending.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortFilteredPersonList(ReadOnlyPerson.PHONESORTDSC);
        SortCommand expectedCommand = prepareCommand(ReadOnlyPerson.PHONESORTDSC, model);
        assertCommandSuccess(expectedCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Comparator namesort = ReadOnlyPerson.NAMESORTASC;
        Comparator phonesort = ReadOnlyPerson.PHONESORTASC;

        SortCommand sortFirstCommand = new SortCommand(namesort);
        SortCommand sortSecondCommand = new SortCommand(phonesort);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(new ClearCommand()));

        // different types -> return false
        assertFalse(sortFirstCommand.equals(true));

        // different comparator -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private SortCommand prepareCommand(Comparator comparator, Model model) {
        SortCommand command = new SortCommand(comparator);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
