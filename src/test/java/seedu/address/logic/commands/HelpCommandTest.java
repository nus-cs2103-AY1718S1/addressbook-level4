package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.model.commandidentifier.CommandIdentifier;
import seedu.address.testutil.CommandIdentifierUtils;
import seedu.address.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_noCommandIdentifier_openHelpWindow() throws Exception {
        CommandResult result = new HelpCommand(CommandIdentifierUtils.createCommandIdentifier("")).execute();

        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_validCommandIdentifier_success() throws Exception {
        CommandResult commandResultWord = getHelpCommand("delete").execute();
        CommandResult commandResultAlias = getHelpCommand("d").execute();

        assertEquals(DeleteCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(DeleteCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);
    }

    @Test
    public void equals() {
        CommandIdentifier delete = CommandIdentifierUtils.createCommandIdentifier(DeleteCommand.COMMAND_WORD);
        CommandIdentifier edit = CommandIdentifierUtils.createCommandIdentifier(EditCommand.COMMAND_WORD);

        HelpCommand helpDeleteCommand = new HelpCommand(delete);
        HelpCommand helpEditCommand = new HelpCommand(edit);

        // same object -> returns true
        assertTrue(helpDeleteCommand.equals(helpDeleteCommand));

        // same values -> returns true
        HelpCommand helpDeleteCommandCopy = new HelpCommand(delete);
        assertTrue(helpDeleteCommand.equals(helpDeleteCommandCopy));

        // different types -> returns false
        assertFalse(helpDeleteCommand.equals(1));

        // null -> returns false
        assertFalse(helpDeleteCommand.equals(null));

        // different command identifier -> returns false
        assertFalse(helpDeleteCommand.equals(helpEditCommand));
    }

    /**
     * Generates a new HelpCommand with the given {@code commandIdentifier}.
     */
    private HelpCommand getHelpCommand(String commandIdentifier) {
        return new HelpCommand(CommandIdentifierUtils.createCommandIdentifier(commandIdentifier));
    }
}


