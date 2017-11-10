package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

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
     *
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

    //@@author qihao27
    /**
     * Returns true if {@code s} represents lower case [OPTION] String
     * e.g. -n, -p, -t, ..., <br>
     * Will return false for any other non-OPTION string input
     * e.g. empty string, " -n " (untrimmed), "- n"(contains whitespace), "- p2" (contains number),
     * "-E" (contains capital letter)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isSortOption(String s) {
        requireNonNull(s);

        try {
            return s.equals("-n") || s.equals("-p") || s.equals("-e") || s.equals("-a") || s.equals("-t");
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents file path
     * e.g. data/addressbook.xml, C:\addressbook.xml, ..., <br>
     * Will return false for any other non-file-path string input
     * e.g. empty string, " data/addressbook.xml " (untrimmed), "data/ addressbook.xml"(contains whitespace),
     * "data/addressbook.doc"(non xml file).
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isFilePath(String s) {
        requireNonNull(s);

        try {
            return s.matches("[\\p{Alnum}][\\p{Graph} ]*[.xml]$");
        } catch (IllegalArgumentException ipe) {
            return false;
        }
    }
    //@@author

}
