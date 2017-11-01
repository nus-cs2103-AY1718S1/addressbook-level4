package seedu.address.logic.trie;

import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.CommandCollection;


/**
 * Class for the autocomplete command function
 * The class holds the trie for command words
 */
public class CommandTrie implements Trie {

    private Node root = null;
    private Set<String> commandSet;
    private Map<String, String> commandMap;
    private CommandCollection commandCollection;

    //@@author grantcm
    public CommandTrie () {
        commandCollection = new CommandCollection ();
        commandSet = commandCollection.getCommandSet();
        commandMap = commandCollection.getCommandMap();

        for (String command : commandSet) {
            this.insert(command);
        }
    }

    /**
     * Indicates whether or note a node is a leaf
     */
    public boolean isLeaf(Node current) {
        return !current.hasNext() && !current.hasChild();
    }

    /**
     * @param input key to autocomplete
     * @return input if the command is not found, otherwise String representation of command word
     */
    public String attemptAutoComplete (String input) throws NullPointerException {
        StringBuilder output = new StringBuilder();

        if (commandSet.contains(input)) {
            //Don't need to traverse trie
            if (commandMap.containsKey (input)) {
                output.append(" ");
                output.append(commandMap.get(input));
            }
        } else {
            //Need to search trie
            char[] inputArray = input.toLowerCase().toCharArray();
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
        }

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

    //@@author
}
