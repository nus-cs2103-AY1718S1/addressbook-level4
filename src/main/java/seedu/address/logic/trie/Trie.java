package seedu.address.logic.trie;

import java.util.Set;

/**
 * Interface for the Command Trie
 */
public interface Trie {
    String attemptAutoComplete (String input);
    void insert (String input);
    Set<String> getCommandSet();
}
