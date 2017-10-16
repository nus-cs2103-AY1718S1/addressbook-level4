package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

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

    /**
     * Returns true if the {@code tagList} contains the {@code word}.
     *   case sensitive and a full word match is required.
     *   <br>examples:<pre>
     *       containsTag(tagList, "abc") == false where tagList does not contain a [abc] tag
     *       containsTag(tagList, "DEF") == true where tagList contains a [DEF] tag
     *       containsTag(tagList, "AB") == false where tagList contains only [ABC] tag
     *       </pre>
     * @param tagList cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsTag(Set<Tag> tagList, String word) {
        requireNonNull(tagList);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        CharSequence space = " ";
        //check if there is more than one tag searched.
        //more than 1 tag searched. split into a list of searches.
        if (preppedWord.contains(space)) {
            String[] separateTags = word.split(" ");
            List<String> tagFilters = Arrays.asList(separateTags);
            for (Tag tag : tagList) {
                if (haveMatchedTags(tagFilters, tag)) {
                    return true;
                }
            }
            return false;
        }
        //only 1 tag searched. Check if tagList contains word as a tag
        try {
            Tag checkTag = new Tag(preppedWord);
            return tagList.contains(checkTag);

        } catch (IllegalValueException e) {
            return false;
        }
    }


    /**
     * Checks if any of the words in tagFilters match with the tag word.
     * Used by containsTag method.
     */
    private static boolean haveMatchedTags(List<String> tagFilters, Tag tag) {
        String encapsulatedTag = tag.toString();
        return tagFilters.contains(encapsulatedTag.substring(1, encapsulatedTag.length() - 1));
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
