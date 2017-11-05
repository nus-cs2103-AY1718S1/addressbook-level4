package seedu.address.logic.trie;

import java.util.List;
import java.util.Set;

/**
 * Interface for the Command Trie
 * Holds all possible commands for the addressbook and provides autocomplete
 * functionality via the attemptAutoComplete function
 */
public interface Trie {
    String attemptAutoComplete (String input);
    void insert (String input);
    List<String> getOptions (String input);
    Set<String> getCommandSet();
}
