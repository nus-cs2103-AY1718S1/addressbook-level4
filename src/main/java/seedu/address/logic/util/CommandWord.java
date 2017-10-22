//@@author A0143832J
package seedu.address.logic.util;

import seedu.address.commons.util.CollectionUtil;

/**
 * a Command Word class with the command word and distance calculated using levenshtein algorithm.
 */
public class CommandWord {
    public final String commandWord;
    public final Integer distance;

    public CommandWord (String commandWord, Integer distance) {
        CollectionUtil.requireAllNonNull(commandWord, distance);
        this.commandWord = commandWord;
        this.distance = distance;
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public Integer getDistance() {
        return this.distance;
    }
}
//@@author
