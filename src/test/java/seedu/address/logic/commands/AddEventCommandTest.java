/* Placeholder unit test
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DINNER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DINNER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DES_APP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DES_DINNER;

import seedu.address.model.event.Event;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
/*
public class AddEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String name = "Some name";
        final String date = "Some date";
        final String description = "Some text";

        assertCommandFailure(prepareCommand(name, date, description), model,
                String.format(MESSAGE_ARGUMENTS, name, date, description));
    }

    @Test
    public void equals() {
        final AddEventCommand standardCommand = new AddEventCommand(VALID_EVENT_NAME_APPOINTMENT, VALID_DATE_APP, VALID_DES_APP);

        // same values -> returns true
        AddEventCommand commandWithSameValues = new AddEventCommand(VALID_EVENT_NAME_APPOINTMENT, VALID_DATE_APP, VALID_DES_APP);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new AddEventCommand(VALID_EVENT_NAME_DINNER, VALID_DATE_APP, VALID_DES_APP)));

        // different date -> returns false
        assertFalse(standardCommand.equals(new AddEventCommand(VALID_EVENT_NAME_APPOINTMENT, VALID_DATE_DINNER, VALID_DES_APP)));

        // different description -> returns false
        assertFalse(standardCommand.equals(new AddEventCommand(VALID_EVENT_NAME_DINNER, VALID_DATE_APP, VALID_DES_DINNER)));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
/*
    private AddEventCommand prepareCommand(String name, String date, String description) {
        AddEventCommand addEventCommand = new AddEventCommand(name, date, description);
        addEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addEventCommand;
    }
}
*/
