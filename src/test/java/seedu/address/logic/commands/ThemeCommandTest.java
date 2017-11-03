package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_DARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.address.logic.commands.ThemeCommand.DARK_THEME;
import static seedu.address.logic.commands.ThemeCommand.LIGHT_THEME;
import static seedu.address.logic.commands.ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SwitchThemeRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author cctdaniel
public class ThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_lightThemeCommand_success() {
        CommandResult result = new ThemeCommand(VALID_THEME_LIGHT).execute();
        assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + LIGHT_THEME + ".", result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SwitchThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_darkThemeCommand_success() {
        CommandResult result = new ThemeCommand(VALID_THEME_DARK).execute();
        assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + DARK_THEME, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SwitchThemeRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
