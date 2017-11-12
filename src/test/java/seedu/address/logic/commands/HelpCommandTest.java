package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author Pengyuz

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void excutesuccess() {
        CommandResult result = new HelpCommand("add").execute();
        assertEquals(AddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("clear").execute();
        assertEquals(ClearCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("delete").execute();
        assertEquals(DeleteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("edit").execute();
        assertEquals(EditCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("exit").execute();
        assertEquals(ExitCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("find").execute();
        assertEquals(FindCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("history").execute();
        assertEquals(HistoryCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("list").execute();
        assertEquals(ListCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("redo").execute();
        assertEquals(RedoCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("select").execute();
        assertEquals(SelectCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("sort").execute();
        assertEquals(SortCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagadd").execute();
        assertEquals(TagAddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagremove").execute();
        assertEquals(TagRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("undo").execute();
        assertEquals(UndoCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-fresh").execute();
        assertEquals(BinclearCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-delete").execute();
        assertEquals(BindeleteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("bin-restore").execute();
        assertEquals(BinrestoreCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("export").execute();
        assertEquals(ExportCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("tagfind").execute();
        assertEquals(TagFindCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("birthdayadd").execute();
        assertEquals(BirthdayAddCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("birthdayremove").execute();
        assertEquals(BirthdayRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("mapshow").execute();
        assertEquals(MapShowCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("maproute").execute();
        assertEquals(MapRouteCommand.MESSAGE_USAGE, result.feedbackToUser);

        result = new HelpCommand("scheduleremove").execute();
        assertEquals(ScheduleRemoveCommand.MESSAGE_USAGE, result.feedbackToUser);

        result =  new HelpCommand("theme").execute();
        assertEquals(SwitchThemeCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
