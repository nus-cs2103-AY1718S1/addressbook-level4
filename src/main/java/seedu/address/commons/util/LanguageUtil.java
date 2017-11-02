//@@author A0143832J
package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.logic.commands.CommandList;
import seedu.address.logic.util.CommandWord;

/**
 * Helper function to enable auto-completition for command box
 */
public class LanguageUtil {
    /**
     * Return a correct command word that is the closet to the one provided by user.
     * @param commandWord by the user
     * @return the closest correct command
     */
    public static String getClosestCommand(String commandWord) {
        commandWord = commandWord.toLowerCase();
        ArrayList<String> commandListString = new CommandList().getCommandList();
        List<CommandWord> commandListCommand = new ArrayList<>();

        for (String command : commandListString) {
            commandListCommand.add(new CommandWord(command,
                    levenshteinDistance(commandWord, commandWord.length(), command, command.length())));
        }
        commandListCommand.sort(Comparator.comparing(CommandWord::getDistance));
        return commandListCommand.get(0).commandWord;
    }

    /**
     * Compare the distance between two string
     * @param s first string
     * @param lenS length of first string
     * @param t second string
     * @param lenT length of second string
     * @return levenshtein distance between 2 strings
     */
    public static Integer levenshteinDistance(String s, Integer lenS, String t, Integer lenT) {
        Integer cost;
        // base case: empty strings
        if (lenS == 0) {
            return lenT;
        }
        if (lenT == 0) {
            return lenS;
        }
        /* test if last characters of the strings match */
        if (s.charAt(lenS - 1) == t.charAt(lenT - 1)) {
            cost = 0;
        } else {
            cost = 1;
        }
        /* return minimum of delete char from s, delete char from t, and delete char from both */
        return Math.min(Math.min(levenshteinDistance(s, lenS - 1, t, lenT) + 1,
                levenshteinDistance(s, lenS, t, lenT - 1) + 1),
                levenshteinDistance(s, lenS - 1, t, lenT - 1) + cost);
    }
}
//@@author
