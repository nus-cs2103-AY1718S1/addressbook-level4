package seedu.address.logic.trie;

public class CommandTrie implements Trie {
    /**
     * Class for the autocomplete command function
     * The class holds the trie for command words
     */
    public CommandTrie () {}


    /**
     *
     * @param input
     * @return input if the command is not found, otherwise String representation of command word
     */
    public String attemptAutoComplete (String input) {
        return input;
    }
}
