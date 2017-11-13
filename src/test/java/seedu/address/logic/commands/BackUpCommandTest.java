package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.model.BackUpEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;
//@@author RonakLakhotia
public class BackUpCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        BackUpCommand command = new BackUpCommand();
        BackUpCommand commandCopy = new BackUpCommand();

        assertFalse(command.equals(null));
        assertFalse(command.equals(0));
        assertTrue(command.equals(command));
        assertTrue(commandCopy.equals(commandCopy));


    }
    @Test
    public void check_backUpEventCollected() throws CommandException {
        BackUpCommand backupCommand = prepareCommand();
        backupCommand.executeUndoableCommand();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BackUpEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    private BackUpCommand prepareCommand() {
        BackUpCommand command = new BackUpCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
