package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.CommandTest;

//@@author khooroko
/**
 * Contains integration tests (interaction with the Model) for {@code ThemeCommand}.
 */
public class ThemeCommandTest extends CommandTest {

    @Test
    public void execute_changeTheme_success() {
        String expectedMessage = ThemeCommand.MESSAGE_SUCCESS;
        ThemeCommand themeCommand = new ThemeCommand();
        assertCommandSuccess(themeCommand, expectedMessage);
    }

    @Test
    public void equals() {
        ThemeCommand themeFirstCommand = new ThemeCommand();
        ThemeCommand themeSecondCommand = new ThemeCommand();

        // same object
        assertTrue(themeFirstCommand.equals(themeFirstCommand));

        // same value
        assertTrue(themeFirstCommand.equals(themeSecondCommand));
    }

    private void assertCommandSuccess(ThemeCommand command, String expectedMessage) {
        // TODO: assert that the theme has actually changed
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
