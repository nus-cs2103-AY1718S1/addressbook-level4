package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;
//@@author kosyoz
public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validTheme_success() {
        assertExecutionSuccess("RedTheme");
        assertExecutionSuccess("DarkTheme");
    }
    @Test
    public void execute_invalidTheme_failure() {
        assertExecutionFailure("" , "Invalid command format!");
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}, and checks that {@code ChangeThemeEvent}
     * is raised with the correct theme.
     */
    private void assertExecutionSuccess(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        try {
            CommandResult commandResult = changeThemeCommand.execute();
            assertEquals(String.format(ChangeThemeCommand.MESSAGE_SUCCESS + theme),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
        ChangeThemeEvent lastEvent = (ChangeThemeEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(theme, (lastEvent.theme));
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code theme}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String theme, String expectedMessage) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        try {
            changeThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            Assert.assertEquals(expectedMessage, ce.getMessage());
        }
    }
}
