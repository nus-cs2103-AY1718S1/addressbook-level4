package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.UserPrefs;

/**
 * @author Sri-vatsa
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UniqueMeetingList(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_list_successfully() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listCheckAlphabeticalSequence() {
        listCommand.executeUndoableCommand();

        String personAFullName;
        String personBFullName;

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            for (int j = i + 1; j < model.getFilteredPersonList().size(); j++) {
                personAFullName = model.getFilteredPersonList().get(i).getName().fullName;
                personBFullName = model.getFilteredPersonList().get(j).getName().fullName;
                assertTrue(personAFullName.compareTo(personBFullName) <=  0);
            }
        }

    }
}
