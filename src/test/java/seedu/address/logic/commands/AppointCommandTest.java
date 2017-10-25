package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appoint;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AppointCommand.
 */
public class AppointCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final AppointCommand standardCommand = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_AMY));

        // same values -> returns true
        AppointCommand commandWithSameValues = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_SECOND_PERSON, new Appoint(VALID_APPOINT_AMY))));

        // different appoints -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_BOB))));
    }

    /**
     * Returns an {@code AppointCommand} with parameters {@code index} and {@code appoint}.
     */
    /*
    private AppointCommand prepareCommand(Index index, String appoint) {
        AppointCommand appointCommand = new AppointCommand(index, new Appoint(appoint));
        appointCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return appointCommand;
    }
    */
}
