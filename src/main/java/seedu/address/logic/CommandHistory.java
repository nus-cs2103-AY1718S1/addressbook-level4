package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);
    }

    /**
     * Clear the command history.
     */
    public void clear() {
        userInputHistory.clear();
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }
}
