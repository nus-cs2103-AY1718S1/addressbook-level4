package seedu.address.logic.commands;

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

    /**
     * Generates a new {@code ExportCommand} which upon execution, exports the contacts in {@code model}.
     */
    private SortCommand prepareCommand(Comparator comparator, Model model) {
        SortCommand command = new SortCommand(comparator);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
