package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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
        preppedWord = preppedWord.replaceAll("[^a-zA-Z0-9\\s]", "");
        preppedWord = preppedWord.trim();

        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1,
                "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.replaceAll("[^a-zA-Z0-9\\s]", "");
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
     * Returns a ArrayList of dates that contains in {@code sentence} in the format [DD/MM/YYYY]
     *
     * <br>examples:<pre>
     *              extractDates("20/10/2017")
     *                          -> returns an ArrayList that contains ["20/10/2017"]
     *              extractDates("20/10/2017 20/10/2017")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2017"]
     *              extractDates("20/10/2017, 20/10/2018")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2018"]
     *              extractDates("20/10/201720/10/2018")
     *                          -> returns an ArrayList that contains ["20/10/2017", "20/10/2018"]
     *              extractDates("20/10/2017 10:15")
     *                          -> returns an ArrayList that contains ["20/10/2017"]
     *              extractDates("20/10/17")
     *                          -> returns an ArrayList that contains []
     *              </pre>
     *
     * @param sentence cannot be null
     */
    public static ArrayList<String> extractDates(String sentence) {
        requireNonNull(sentence);

        ArrayList<String> extractedDates = new ArrayList<>();
        String preppedSentence = sentence.trim();

        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\/\\d{2}\\/\\d{4})");
            Matcher matcher = pattern.matcher(preppedSentence);

            while (matcher.find()) {
                String validDateRegex = "^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$";

                if (matcher.group(1).matches(validDateRegex)) {
                    extractedDates.add(matcher.group(1));
                }
            }
        }

        return extractedDates;
    }

    /**
     * Returns a ArrayList of times that contains in {@code sentence} in the format [HH:mm]
     * <br>examples:<pre>
     *              extractDates("05:50")
     *                          -> returns an ArrayList that contains ["05:50"]
     *              extractDates("21:01 21:03")
     *                          -> returns an ArrayList that contains ["21:01", "21:03"]
     *              extractDates("21:01, 21:03")
     *                          -> returns an ArrayList  that contains ["21:01", "21:03"]
     *              extractDates("10:5010:10")
     *                          -> returns an ArrayList that contains ["10:50", "10:10"]
     *              extractDates("20/10/2017 10:15")
     *                          -> returns an ArrayList that contains ["10:15"]
     *              extractDates("5:15")
     *                          -> returns an ArrayList that contains []
     *              </pre>
     * @param sentence cannot be null
     */
    public static ArrayList<String> extractTimes(String sentence) {
        requireNonNull(sentence);

        ArrayList<String> extractedTimes = new ArrayList<>();
        String preppedSentence = sentence.trim();

        if (!preppedSentence.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\d{2}\\:\\d{2})");
            Matcher matcher = pattern.matcher(preppedSentence);

            while (matcher.find()) {
                String validTimeRegex = "^(0[0-9]|[1][0-9]|[2][0-3])[:](0[0-9]|[1-5][0-9])$";

                if (matcher.group(1).matches(validTimeRegex)) {
                    extractedTimes.add(matcher.group(1));
                }
            }
        }

        return extractedTimes;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * Format of date: DD/MM/YYY
     * <br>examples:<pre>
     *       containsDate("20/10/2017 15:30", "20/10/2017") == true
     *       containsDate("20/10/2017 15:30", "15:30") == false
     *       containsDate("20/10/2017 15:30", "20//10/2017 14:30") == true
     *       containsDate("20/10/2017 14:30", "20/10") == false //not a full date or time match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsDate(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.trim();

        ArrayList<String> extractedDates = extractDates(preppedSentence);
        ArrayList<String> dateInPreppedSentence = new ArrayList<>();
        dateInPreppedSentence.addAll(extractedDates);

        for (String dateInSentence : dateInPreppedSentence) {
            if (dateInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * Format of time: HH:mm
     * <br>examples:<pre>
     *       containsDateAndTime("20/10/2017 15:30", "15:30") == true
     *       containsDateAndTime("20/10/2017 15:30", "20//10/2017 14:30") == true
     *       containsDateAndTime("20/10/2017 14:30", "14:3") == false //not a full date or time match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsTime(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        preppedSentence = preppedSentence.trim();

        ArrayList<String> extractedTimes = extractTimes(preppedSentence);
        ArrayList<String> timeInPreppedSentence = new ArrayList<>();
        timeInPreppedSentence.addAll(extractedTimes);

        for (String timeInSentence : timeInPreppedSentence) {
            if (timeInSentence.equalsIgnoreCase(preppedWord)) {
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
