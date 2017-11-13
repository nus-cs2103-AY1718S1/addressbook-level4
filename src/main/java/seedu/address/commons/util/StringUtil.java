package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    //@@author CindyTsai1
    /**
     * Returns true if the {@code sentence} equals the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc def") == true
     *       containsWordIgnoreCase("ABc def", "abc DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedSentence = sentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");
        String preppedWord = word.trim();
        String[] wordsInPreppedWord = preppedWord.split("\\s+");
        checkArgument(!wordsInPreppedWord[0].equals(""), "Word parameter cannot be empty");
        //checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        //check if the names and the word have the same number of words
        if (wordsInPreppedSentence.length != wordsInPreppedWord.length) {
            return false;
        }

        //check if all words in sentence matches all words in word
        for (int i = 0; i < wordsInPreppedSentence.length; i++) {
            if (!wordsInPreppedSentence[i].equalsIgnoreCase(wordsInPreppedWord[i])) {
                return false;
            }
        }
        return true;
    }

    //@@author
    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
