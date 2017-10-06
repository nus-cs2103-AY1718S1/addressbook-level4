package seedu.address.logic.trie;

/**
 * Interface for the Command Trie
 */
public interface Trie {
    String attemptAutoComplete (String input);
    void insert (String input);
}
