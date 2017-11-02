package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.LogoutCommand.MESSAGE_LOGOUT_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.LogoutAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author jelneo
public class LogoutCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_logout_success() {
        LogoutCommand logoutCommand = new LogoutCommand();
        logoutCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = logoutCommand.execute();
        assertEquals(MESSAGE_LOGOUT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof LogoutAppRequestEvent);
    }
}
