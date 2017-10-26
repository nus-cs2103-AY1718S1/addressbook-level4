package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import seedu.address.model.person.email.Email;
import seedu.address.model.tag.Tag;


/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsNameIgnoreCase("ABc def", "abc") == true
     *       containsNameIgnoreCase("ABc def", "DEF") == true
     *       containsNameIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence   cannot be null
     * @param searchWord cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsNameIgnoreCase(String sentence, String searchWord) {
        requireNonNull(sentence);
        requireNonNull(searchWord);

        String preppedWord = searchWord.trim();
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
     * Returns true if the {@code emailsentence} contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsEmailgnoreCase("abc@example.com", "e/example") == true
     *       containsEmailgnoreCase(("abc@example.com", "example") == false
     *       containsEmailgnoreCase(("abc@example.com", "e/   EXAMPLE") == true//Case insensitive
     *       </pre>
     *
     * @param emailSet cannot be null
     * @param searchWord    cannot be null, cannot be empty, must be a valid  email domain
     */
    public static boolean containsEmailIgnoreCase(Collection<Email> emailSet, String searchWord) {
        requireNonNull(emailSet);
        requireNonNull(searchWord);

        String preppedWord = searchWord.trim();
        checkArgument(!preppedWord.isEmpty(), "Email parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Email parameter should be a single word");

        for (Email email : emailSet) {
            String emailString = email.value;
            String preppedEmailSentence = emailString.substring(emailString.indexOf('@') + 1);
            String finalPreppedEmailSentence = preppedEmailSentence.substring(0, preppedEmailSentence.indexOf('.'));

            if (finalPreppedEmailSentence.equalsIgnoreCase(preppedWord.toLowerCase())) {
                return true;
            }
        }

        return false;
    }


    /**
     * Returns true if the {@code phoneSentence} contains the {@code searchWord}.
     * Ignores case, but numbers must be either 4 digits or 8 digits.
     * <br>examples:<pre>
     *      containsPhoneIgnoreCase("99998888", "p/8888") == true
     *      containsPhoneIgnoreCase("99998888", "e/9999 8888") == true
     *      containsPhoneIgnoreCase(("99998888", "999 8888") == false
     *       </pre>
     *
     * @param phoneSentence cannot be null
     * @param searchWord    cannot be null, cannot be empty, must be 4 digits or 8 digits only
     */
    public static boolean containsPhoneIgnoreCase(String phoneSentence, String searchWord) {
        requireNonNull(phoneSentence);
        requireNonNull(searchWord);

        String preppedPhone = searchWord.trim();
        checkArgument(!preppedPhone.isEmpty(), "Phone parameter cannot be empty");
        checkArgument(preppedPhone.split("\\s+").length == 1, "Phone numbers parameter should be a single word");
        String[] phonePreppedSentence = phoneSentence.split("(?<=\\G.{4})");


        for (String phoneInSentence : phonePreppedSentence) {
            if (phoneInSentence.matches(preppedPhone)) {
                return true;

            }
        }
        return false;
    }


    /**
     * Returns true if the {@code addressSentence } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "a/#08-111") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "a/#08-111","123") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "e/JUrong") == true
     *      containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "e/JUrongWest") == false
     *       </pre>
     *
     * @param addressSentence cannot be null
     * @param searchWord      cannot be null, cannot be empty, must be 4 digits or 8 digits
     */
    public static boolean containsAddressIgnoreCase(String addressSentence, String searchWord) {
        requireNonNull(addressSentence);
        requireNonNull(searchWord);

        String preppedAddress = searchWord.trim();
        checkArgument(!preppedAddress.isEmpty(), "Address parameter cannot be empty");
        checkArgument(preppedAddress.split("\\s+").length == 1, "Address parameter should be a single word");


        List<String> tempAddress = Arrays.asList(addressSentence.replaceAll(",", "").split("\\s+"));
        return tempAddress.stream().anyMatch(preppedAddress::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code tagSet } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsTagIgnoreCase("friends", "a/FRIend") == true
     *      containsTagIgnoreCase("friends", "e/FRIENd") == false
     *       </pre>
     *
     * @param tagSet     cannot be null
     * @param searchWord cannot be null, cannot be empty, must be at least a a single word
     */
    public static boolean containsTagIgnoreCase(Collection<Tag> tagSet, String searchWord) {
        requireAllNonNull(tagSet);
        requireNonNull(searchWord);

        String preppedTag = searchWord.trim();
        checkArgument(!preppedTag.isEmpty(), "Tag parameter cannot be empty");
        checkArgument(preppedTag.split("\\s+").length == 1, "Tag parameter should be a single word");
        String[] tempTag = preppedTag.split("\\s+");

        for (Tag tag : tagSet) {
            for (String tagToCheck : tempTag) {
                if (tagToCheck.equalsIgnoreCase(tag.tagName)) {
                    return true;
                }
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
