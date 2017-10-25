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
        CommandResult commandResultWord = getHelpCommand("help").execute();
        CommandResult commandResultAlias = getHelpCommand("h").execute();
        assertEquals(HelpCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(HelpCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("add").execute();
        commandResultAlias = getHelpCommand("a").execute();
        assertEquals(AddCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(AddCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("list").execute();
        commandResultAlias = getHelpCommand("l").execute();
        assertEquals(ListCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(ListCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("edit").execute();
        commandResultAlias = getHelpCommand("e").execute();
        assertEquals(EditCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(EditCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("copy").execute();
        commandResultAlias = getHelpCommand("y").execute();
        assertEquals(CopyCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(CopyCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("find").execute();
        commandResultAlias = getHelpCommand("f").execute();
        assertEquals(FindCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(FindCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("delete").execute();
        commandResultAlias = getHelpCommand("d").execute();
        assertEquals(DeleteCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(DeleteCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("locate").execute();
        commandResultAlias = getHelpCommand("lc").execute();
        assertEquals(LocateCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(LocateCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("history").execute();
        commandResultAlias = getHelpCommand("his").execute();
        assertEquals(HistoryCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(HistoryCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("undo").execute();
        commandResultAlias = getHelpCommand("u").execute();
        assertEquals(UndoCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(UndoCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("redo").execute();
        commandResultAlias = getHelpCommand("r").execute();
        assertEquals(RedoCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(RedoCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("clear").execute();
        commandResultAlias = getHelpCommand("c").execute();
        assertEquals(ClearCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(ClearCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("exit").execute();
        commandResultAlias = getHelpCommand("q").execute();
        assertEquals(ExitCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(ExitCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);

        commandResultWord = getHelpCommand("schedule").execute();
        commandResultAlias = getHelpCommand("sc").execute();
        assertEquals(ScheduleCommand.MESSAGE_USAGE, commandResultWord.feedbackToUser);
        assertEquals(ScheduleCommand.MESSAGE_USAGE, commandResultAlias.feedbackToUser);
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


