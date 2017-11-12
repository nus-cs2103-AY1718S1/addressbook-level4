package seedu.address.logic.commands;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

//@@author Sri-vatsa
/**
 * Tests if setup Asana command is successful
 */
public class SetupAsanaCommandTest {

    @Test
    public void setup_asana_success() throws Exception {
        CommandResult commandResult = new SetupAsanaCommand().execute();
        assertEquals(SetupAsanaCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

}