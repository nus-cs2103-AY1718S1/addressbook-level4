package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.logic.commands.AddRelationshipCommand;

//@@author joanneong
/**
 * A series of tests to test the information display function.
 *
 * Each test is related to multiple components in Intelli, namely {@code CommandBox} and {@code ResultDisplay}.
 */
public class InfoDisplaySystemTest extends AddressBookSystemTest {

    private static final String COMMAND_THAT_FAILS = "@#!";
    private static final String COMMAND_THAT_SUCCEEDS = AddRelationshipCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_SUCCEEDS_ALIAS = AddRelationshipCommand.COMMAND_ALIAS;
    private static final String SHORT_COMMAND_USAGE = AddRelationshipCommand.SHORT_MESSAGE_USAGE;
    private static final String FORMATTED_SHORT_COMMAND_USAGE = "How to use:\n" + SHORT_COMMAND_USAGE;

    @Test
    public void informationDisplay() {
        CommandBoxHandle commandBoxHandle = getCommandBox();
        ResultDisplayHandle resultDisplayHandle = getInfoDisplay();

        //Case: no command in the command box
        commandBoxHandle.enterInputWithoutAutocompletion("");
        assertEquals("", resultDisplayHandle.getText());

        //Case: no corresponding information displayed
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_FAILS);
        assertEquals("", resultDisplayHandle.getText());

        //Case: corresponding usage information displayed for typed command
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_SUCCEEDS);
        assertEquals(FORMATTED_SHORT_COMMAND_USAGE, resultDisplayHandle.getText());

        //Case: corresponding usage information displayed for typed alias
        commandBoxHandle.enterInputWithoutAutocompletion(COMMAND_THAT_SUCCEEDS_ALIAS);
        assertEquals(FORMATTED_SHORT_COMMAND_USAGE, resultDisplayHandle.getText());
    }
}
