package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author kenpaxtonlim
public class QuickHelpCommandTest {

    @Test
    public void execute_command_success() {
        QuickHelpCommand command = new QuickHelpCommand();

        try {
            CommandResult commandResult = command.execute();
            assertEquals(QuickHelpCommand.MESSAGE, commandResult.feedbackToUser);
        } catch (CommandException e) {
            throw new IllegalArgumentException("Execution of command should not fail.", e);
        }
    }

    @Test
    public void equals() {
        QuickHelpCommand command = new QuickHelpCommand();

        // same object -> returns true
        assertTrue(command.equals(command));

        // same type different objects -> returns true
        assertTrue(command.equals(new QuickHelpCommand()));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));
    }
}
