package seedu.address.logic.trie;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Class for the autocomplete command function
 * The class holds the trie for command words
 */
public class CommandTrie implements Trie {

    private Node root = null;
    private Set<String> commandSet = Stream.of(
            AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD
    ).collect(Collectors.toSet());

    public CommandTrie () {
        for (String command : commandSet) {
            this.insert(command);
        }
    }

    /**
     * Indicates whether or note a node is a leaf
     */
    public boolean isLeaf(Node current) {
        if (!current.hasNext() && !current.hasChild()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param input key to autocomplete
     * @return input if the command is not found, otherwise String representation of command word
     */
    public String attemptAutoComplete (String input) throws NullPointerException {
        char[] inputArray = input.toLowerCase().toCharArray();
        StringBuilder output = new StringBuilder();
        Node temp = root;
        int i = 0;

        while (!isLeaf(temp)) {
            if (i < inputArray.length) {
                if (temp.getKey() == inputArray[i]) {
                    output.append(inputArray[i]);
                    temp = temp.getChild();
                    i++;
                } else {
                    temp = temp.getNext();
                }
            } else if (temp.hasNext()) {
                return input;
            } else {
                output.append(temp.getKey());
                temp = temp.getChild();
            }
        }
        output.append(temp.getKey());

        return output.toString();
    }

    /**
     * Insert function for trie
     * @param input key
     */

    public void insert (String input) {
        char[] inputArray = input.toCharArray();

        if (root == null) {
            root = new Node (inputArray[0], null, null);
            Node temp = root;
            for (int i = 1; i < inputArray.length; i++) {
                temp.setChild(new Node(inputArray[i], null, null));
                temp = temp.getChild();
            }
        } else {
            Node temp = root;
            int i = 0;
            //Navigate to leaf of trie
            while (temp.hasNext()) {
                if (temp.getKey() == inputArray[i]) {
                    temp = temp.getChild();
                    i++;
                } else {
                    temp = temp.getNext();
                }
            }

            for (; i < inputArray.length - 1; ) {
                if (temp.getKey() == inputArray[i] && temp.hasChild()) {
                    //Navigate to existing child
                    i++;
                    temp = temp.getChild();
                } else if (temp.getKey() == inputArray[i] && !temp.hasChild()) {
                    //Add child
                    i++;
                    temp.setChild(new Node(inputArray[i], null, null));
                    temp = temp.getChild();
                } else {
                    //Add next
                    temp.setNext(new Node(inputArray[i], null, null));
                    temp = temp.getNext();
                }
            }
        }
    }

    public Set<String> getCommandSet() {
        return commandSet;
    }
}
