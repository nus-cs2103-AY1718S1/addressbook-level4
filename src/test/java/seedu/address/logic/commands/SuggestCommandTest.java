package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.SuggestCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author CindyTsai1
/**
 * Contains integration tests (interaction with the Model) for {@code SuggestCommand}.
 */
public class SuggestCommandTest {
    @Test
    public void execute_suggest_success() {
        String command = "edit";
        String expectedMessage = String.format(MESSAGE_SUCCESS, command);

        // assert that suggest command is executed
        try {
            new SuggestCommand(command).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}

