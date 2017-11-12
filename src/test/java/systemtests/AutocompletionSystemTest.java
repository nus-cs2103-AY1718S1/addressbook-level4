package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import seedu.address.logic.commands.AddRelationshipCommand;

//@@author joanneong
/**
 * A series of tests to test the auto-completion function.
 *
 * Each test is related to multiple components in Intelli, namely {@code CommandBox} and {@code ResultDisplay}.
 */
public class AutocompletionSystemTest extends AddressBookSystemTest {

    private static final String COMMAND_THAT_FAILS = "@#!";
    private static final String COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD;
    private static final String INCOMPLETE_COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD.substring(0, 4);
    private static final String SHORT_INCOMPLETE_COMMAND_THAT_SUCCEEDS =
            AddRelationshipCommand.COMMAND_WORD.substring(0, 1);

    @Test
    public void checkAutocompletion() {
        CommandBoxHandle commandBoxHandle = getCommandBox();

        //Case: no autocomplete suggestion
        commandBoxHandle.enterInput(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());

        //Case: single autocomplete suggestion
        commandBoxHandle.enterInput(INCOMPLETE_COMMAND_THAT_SUCCEEDS);
        assertEquals(COMMAND_THAT_SUCCEEDS, commandBoxHandle.getInput());

        //Case: multiple autocomplete suggestions
        commandBoxHandle.enterInput(SHORT_INCOMPLETE_COMMAND_THAT_SUCCEEDS);
        assertEquals("add", commandBoxHandle.getInput());
    }

}
