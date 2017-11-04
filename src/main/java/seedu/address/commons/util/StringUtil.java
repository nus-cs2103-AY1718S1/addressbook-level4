package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    //@@author KhorSL
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCaseAndCharacters("ABc def", "abc") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def", "DEF") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def.", "DEF") == true
     *       containsWordIgnoreCaseAndCharacters("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCaseAndCharacters(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();

        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.replaceAll("\\W", " ");
        preppedSentence = preppedSentence.trim();
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsDateAndTime("20/10/2017 15:30", "20/10/2017") == true
     *       containsDateAndTime("20/10/2017 15:30", "15:30") == true
     *       containsDateAndTime("20/10/2017 15:30", "20//10/2017 14:30") == true
     *       containsDateAndTime("20/10/2017 14:30", "20/10") == false //not a full date or time match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsDateAndTime(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.trim();

        String preppedDate = "DD/MM/YYYY";
        String preppedTime = "TT:TT";
        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\/\\d{2}\\/\\d{4})");
            Matcher matcher = pattern.matcher(preppedSentence);
            if (matcher.find()) {
                preppedDate = matcher.group(1);
            }
        }

        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\:\\d{2})");
            Matcher matcher = pattern.matcher(preppedSentence);
            if (matcher.find()) {
                preppedTime = matcher.group(1);
            }
        }

        String[] dateAndTimeInPreppedSentence = new String[2];
        dateAndTimeInPreppedSentence[0] = preppedDate;
        dateAndTimeInPreppedSentence[1] = preppedTime;
        for (String dateOrTimeInSentence : dateAndTimeInPreppedSentence) {
            if (dateOrTimeInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
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
}
