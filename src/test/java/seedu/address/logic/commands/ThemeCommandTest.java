package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import guitests.AddressBookGuiTest;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author teclu
public class ThemeCommandTest extends AddressBookGuiTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private String[] listThemes = { "Light", "Dark", "Red", "Blue", "Green" };

    @Test
    public void execute_validTheme_success() {
        for (int i = 4; i >= 0; i--) {
            assertExecutionSuccess(listThemes[i]);
        }
    }

    @Test
    public void execute_invalidTheme_failure() {
        assertExecutionFailure("NotATheme", Messages.MESSAGE_INVALID_THEME);
    }

    @Test
    public void equals() {
        ThemeCommand[] listThemeCommands = new ThemeCommand[5];
        for (int i = 0; i < 5; i++) {
            listThemeCommands[i] = new ThemeCommand(listThemes[i]);
        }

        // same object -> returns true
        for (int i = 0; i < 5; i++) {
            assertTrue(listThemeCommands[i].equals(new ThemeCommand(listThemes[i])));
        }

        // different types -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(1));
        }

        // null -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(null));
        }

        // different theme -> returns false
        int j = 4;

        for (int i = 0; i < 5; i++) {
            if (i != j) {
                assertFalse(listThemeCommands[i].equals(listThemeCommands[j]));
            }
            j--;
        }
    }

    /**
     * Executes a {@code ThemeCommand} with the given {@code theme}, and checks that
     * {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(String theme) {
        ThemeCommand themeCommand = new ThemeCommand(theme);

        try {
            CommandResult commandResult = themeCommand.execute();
            assertEquals(String.format(ThemeCommand.MESSAGE_THEME_SUCCESS, theme),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeThemeRequestEvent lastEvent =
                (ChangeThemeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(theme, lastEvent.theme);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String theme, String expectedMessage) {
        ThemeCommand themeCommand = new ThemeCommand(theme);

        try {
            themeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
