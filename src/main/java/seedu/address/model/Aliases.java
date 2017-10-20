package seedu.address.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Contains a mapping of aliases to their commands, which is referred to when parsing commands.
 * When using the Alias and Unalias commands, key-value pairs are added and removed respectively.
 */
public class Aliases {

    private final Map<String, String> map = new HashMap<>();

    /*
     * We initialise the map with aliases for frequently used commands. Users can add other aliases themselves.
     */
    public Aliases() {
        map.put("a", AddCommand.COMMAND_WORD);
        map.put("d", DeleteCommand.COMMAND_WORD);
        map.put("e", EditCommand.COMMAND_WORD);
        map.put("f", FindCommand.COMMAND_WORD);
        map.put("h", HelpCommand.COMMAND_WORD);
        map.put("l", ListCommand.COMMAND_WORD);
        map.put("r", RedoCommand.COMMAND_WORD);
        map.put("s", SelectCommand.COMMAND_WORD);
        map.put("u", UndoCommand.COMMAND_WORD);
    }

    /**
     * Returns a set of all aliases
     */
    public Set<String> getAllAliases() {
        return map.keySet();
    }

    /**
     * Returns the command linked to a specific alias, or null otherwise.
     */
    public String getCommand(String alias) {
        return map.get(alias);
    }

    /**
     * Adds or updates an alias to the map.
     */
    public void addAlias(String alias, String command) {
        map.put(alias, command);
    }

    /**
     * Removes an alias from the map.
     *
     * @throws NoSuchElementException if no such alias exists
     */
    public boolean removeAlias(String alias) throws NoSuchElementException {
        if (map.remove(alias) == null) {
            throw new NoSuchElementException();
        }
        return true;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Aliases // instanceof handles nulls
                && this.map.equals(((Aliases) other).map));
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
