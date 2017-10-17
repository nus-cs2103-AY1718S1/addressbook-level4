package seedu.address.commons.util;

import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsNameIgnoreCase("ABc def", "abc") == true
     *       containsNameIgnoreCase("ABc def", "DEF") == true
     *       containsNameIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsNameIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String[] wordsInPreppedSentence = sentence.split("\\s+");

        for (String wordInSentence : wordsInPreppedSentence) {
            if (wordInSentence.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the {@code emailsentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsEmailgnoreCase("abc@example.com", "e/example") == true
     *       containsEmailgnoreCase(("abc@example.com", "example") == false
     *       containsEmailgnoreCase(("abc@example.com", "e/   EXAMPLE") == true//Case insensitive
     *       </pre>
     *
     * @param emailSentence cannot be null
     * @param word          cannot be null, cannot be empty, must be a valid  email domain
     */
    public static boolean containsEmailIgnoreCase(String emailSentence, String word) {
        requireNonNull(emailSentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Email parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Email parameter should have a valid email domain");

        String preppedEmailSentence = emailSentence.substring(emailSentence.indexOf('@') + 1);
        String finalPreppedEmailSentence = preppedEmailSentence.substring(0, preppedEmailSentence.indexOf('.'));

        return finalPreppedEmailSentence.equalsIgnoreCase(preppedWord.toLowerCase());

    }


    /**
     * Returns true if the {@code phoneSentence} contains the {@code word}.
     * Ignores case, but numbers must be either 4 digits or 8 digits.
     * <br>examples:<pre>
     *      containsPhoneIgnoreCase("99998888", "p/8888") == true
     *      containsPhoneIgnoreCase("99998888", "e/9999 8888") == true
     *      containsPhoneIgnoreCase(("99998888", "999 8888") == false
     *       </pre>
     *
     * @param phoneSentence cannot be null
     * @param word          cannot be null, cannot be empty, must be 4 digits or 8 digits
     */
    public static boolean containsPhoneIgnoreCase(String phoneSentence, String word) {
        requireNonNull(phoneSentence);
        requireNonNull(word);

        String preppedPhone = word.trim();
        checkArgument(!preppedPhone.isEmpty(), "Phone parameter cannot be empty");
        checkArgument(preppedPhone.split("(?<=\\G.{4})").length == 1, "Phone parameter should have at least 4 digits");
        String[] phonePreppedSentence = phoneSentence.split("(?<=\\G.{4})");


        for (String phoneInSentence : phonePreppedSentence) {
            if (phoneInSentence.matches(preppedPhone)) {
                return true;

            }
        }
        return false;
    }


    /**
     * Returns true if the {@code addressSentence } contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsAddressIgnoreCase("99998888", "a/Geylang") == true
     *      containsAddressIgnoreCase("99998888", "e/Gey lanf") == false
     *       </pre>
     *
     * @param addressSentence cannot be null
     * @param word            cannot be null, cannot be empty, must be 4 digits or 8 digits
     */
    public static boolean containsAddressIgnoreCase(String addressSentence, String word) {
        requireNonNull(addressSentence);
        requireNonNull(word);

        String preppedAddress = word.trim();
        checkArgument(!preppedAddress.isEmpty(), "Address parameter cannot be empty");
        checkArgument(preppedAddress.split("\\s+").length ==1, "Address parameter should be a single word");


        List<String> tempAddress = Arrays.asList(addressSentence.split("\\s+"));
        return tempAddress.stream().anyMatch(preppedAddress::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code tagSet } contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsAddressIgnoreCase("99998888", "a/Geylang") == true
     *      containsAddressIgnoreCase("99998888", "e/Gey lanf") == false
     *       </pre>
     *
     * @param tagSet cannot be null
     * @param word   cannot be null, cannot be empty, must be at least a a single word
     */
    public static boolean containsTagIgnoreCase(Collection<Tag> tagSet, String word) {
        requireAllNonNull(tagSet);
        requireNonNull(word);

        String preppedTag = word.trim();
        checkArgument(!preppedTag.isEmpty(), "Tag parameter cannot be empty");
        checkArgument(preppedTag.split("\\s+").length == 1, "Tag parameter should be a single word");
        String[] tempTag = preppedTag.split("\\s+");

        for (Tag tag: tagSet) {
            for (String tagToCheck : tempTag) {
                if (tagToCheck.equalsIgnoreCase(tag.tagName))
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
}
