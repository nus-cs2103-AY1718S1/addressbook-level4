package seedu.address.logic.commands;
//@@author zhoukai07
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIntegers.INTEGER_FIRST_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_FOURTH_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_SECOND_THEME;
import static seedu.address.testutil.TypicalIntegers.INTEGER_THIRD_THEME;
import static seedu.address.testutil.TypicalIntegers.OUT_OF_BOUND_THEME;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ChangeThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validTheme_success() {
        execute_changeToTheme_success(INTEGER_FIRST_THEME);
        execute_changeToTheme_success(INTEGER_SECOND_THEME);
        execute_changeToTheme_success(INTEGER_THIRD_THEME);
        execute_changeToTheme_success(INTEGER_FOURTH_THEME);
    }
    @Test
    public void execute_invalidTheme_failure() {
        Integer outOfBoundsInteger = OUT_OF_BOUND_THEME;
        assertExecutionFailure(outOfBoundsInteger, Messages.MESSAGE_INVALID_THEME_INDEX);
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code integer}.
     */
    private void execute_changeToTheme_success(Integer integer) {
        ChangeThemeCommand changeThemeCommand = prepareCommand(integer);
        try {
            CommandResult result = changeThemeCommand.execute();
            assertEquals(String.format(ChangeThemeCommand.MESSAGE_SELECT_THEME_SUCCESS, integer),
                    result.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeThemeRequestEvent);
    }
    /**
     * Executes a {@code ChangeThemeCommand} with the given {@code integer}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Integer index, String expectedMessage) {
        ChangeThemeCommand changeThemeCommand = prepareCommand(index);

        try {
            changeThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ChangeThemeCommand} with parameters {@code integer}.
     */
    private ChangeThemeCommand prepareCommand(Integer integer) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(integer);
        return changeThemeCommand;
    }
}
//@@author
