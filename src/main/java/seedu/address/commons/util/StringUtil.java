package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        for (String wordInSentence: wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }
    //@@author k-l-a
    /**
     * Returns true if the sentence contains the word
     *  Ignores case, and a full word match is not required
     *   <br>examples:<pre>
     *       containsWordPartialIgnoreCase("ABc def", "abc") == true
     *       containsWordPartialIgnoreCase("ABc def", "DEF") == true
     *       containsWordPartialIgnoreCase("ABc def", "AB") == true //  query partially match sentence
     *       containsWordPartialIgnoreCase("ABc def", "AbCD") == false // sentence only partially match query
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordPartialIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.trim().toLowerCase();
        return preppedSentence.contains(preppedWord);
    }

    /**
     * Returns the earliest (starting) position in the sentence that matches with at least one of the keywords in words,
     * or -1 if there are no matches.
     * Ignores case, and a full word match is not required
     * example : Say listOfWords is a List that contains the strings "Mat" and "Sen"
     *      earliestIndexOf("tomatch", listOfWords) --> returns 2, matching "Mat"
     *      earliestIndexOf("sentenceMatch", listOfWords) --> returns 0, matches both but "Sen" is matched at the start
     *      earliestIndexOf("notCorrect", listOfWords) --> returns -1, no match
     * @param sentence
     * @param words
     * @return
     */
    public static int earliestIndexOf(String sentence, List<String> words) {
        requireNonNull(sentence);
        requireNonNull(words);

        String[] preppedWords = new String[words.size()];
        for (int i = 0; i < words.size(); i++) {
            String preppedWord = words.get(i).trim();
            checkArgument(!preppedWord.isEmpty(), "Word parameter cannot contain an empty string");
            checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");
            preppedWords[i] = preppedWord;
        }

        int earliestIndex = -1;
        for (String keyword: preppedWords) {
            int index = sentence.toLowerCase().indexOf(keyword);
            if (index == 0) {
                return index;
            } else if (earliestIndex < 0 || (index >= 0 && index < earliestIndex)) {
                earliestIndex = index;
            }
        }

        return earliestIndex;
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
