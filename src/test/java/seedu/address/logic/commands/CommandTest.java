package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests certain fields in the Command class
 */
public class CommandTest {
    //@@author lancehaoh
    @Test
    /**
     * Check for duplicate command aliases.
     */
    public void executeFindDuplicateCommandAliasesErrorIfFound() {
        Set<String> uniqueAliases = new HashSet<>();
        uniqueAliases.addAll(Command.getListOfAvailableCommandAliases());
        assertEquals(Command.getListOfAvailableCommandAliases().size(), uniqueAliases.size());
    }

    //@@author lancehaoh
    @Test
    /**
     * Check if every command has defined a help string
     */
    public void executeCheckHelpForEveryCommand() {
        Map<String, String> mapOfCommandsToHelp = Command.getMapOfCommandHelp();
        List<String> listOfAvailableCommands = new ArrayList<>(Command.getMapOfCommandFormats().keySet());

        // Check if every command maps to a help string
        for (String s : listOfAvailableCommands) {
            assertTrue(mapOfCommandsToHelp.containsKey(s));
        }
    }
}
