package seedu.address.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Map of aliases to their commands
 */
public class Aliases {

    private final Map<String, String> map;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        map = new HashMap<String, String>();
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

    //// util methods

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
