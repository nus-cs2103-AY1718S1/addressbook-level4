package seedu.address.model.commandidentifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
        assertTrue(CommandIdentifier.isValidCommandIdentifier("delete"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("d"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("add"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("a"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("edit"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("e"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("exit"));
        assertTrue(CommandIdentifier.isValidCommandIdentifier("q"));
    }
}
