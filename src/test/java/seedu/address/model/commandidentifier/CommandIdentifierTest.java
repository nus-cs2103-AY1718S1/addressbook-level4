package seedu.address.model.commandidentifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;

public class CommandIdentifierTest {

    @Test
    public void isValidCommandIdentifier() {
        // invalid command identifiers
        assertFalse(CommandIdentifier.isValidCommandIdentifier(""));
        assertFalse(CommandIdentifier.isValidCommandIdentifier(" "));
        assertFalse(CommandIdentifier.isValidCommandIdentifier("evolve"));
        assertFalse(CommandIdentifier.isValidCommandIdentifier("z"));
        assertFalse(CommandIdentifier.isValidCommandIdentifier("asdacr"));

        // valid command identifiers
        assertTrue(CommandIdentifier.isValidCommandIdentifier(DeleteCommand.COMMAND_WORD));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(DeleteCommand.COMMAND_ALIAS));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(AddCommand.COMMAND_WORD));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(AddCommand.COMMAND_ALIAS));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(EditCommand.COMMAND_WORD));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(EditCommand.COMMAND_ALIAS));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(ExitCommand.COMMAND_WORD));
        assertTrue(CommandIdentifier.isValidCommandIdentifier(ExitCommand.COMMAND_ALIAS));
    }
}
