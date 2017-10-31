package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import seedu.address.model.person.email.Email;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;



/**
 * Helper functions for handling strings for findCommand.
 */
public class PredicateUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsAttributeIgnoreCase("ABc def", "abc") == true
     *       containsAttributeIgnoreCase("ABc def", "DEF") == true
     *       containsAttributeIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence   cannot be null
     * @param searchWord cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsAttributeIgnoreCase(String sentence, String searchWord) {
        keywordsPredicateCheckForNull(sentence, searchWord);

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
     * @param emailSet   cannot be null
     * @param searchWord cannot be null, cannot be empty, must be a valid  email domain
     */
    public static boolean containsEmailIgnoreCase(Collection<Email> emailSet, String searchWord) {
        keywordsPredicateCheckForNull(emailSet, searchWord);

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
        keywordsPredicateCheckForNull(phoneSentence, searchWord);

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
        keywordsPredicateCheckForNull(addressSentence, searchWord);

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
        keywordsPredicateCheckForNull(tagSet, searchWord);

        String preppedTag = searchWord.trim();
        checkArgument(!preppedTag.isEmpty(), "Tag parameter cannot be empty");
        checkArgument(preppedTag.split("\\s+").length == 1, "Tag parameter should be a single word");

        for (Tag tag : tagSet) {
            if (preppedTag.equalsIgnoreCase(tag.tagName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns true if the {@code tagSet } contains the {@code searchWord}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *      containsTagIgnoreCase("friends", "a/FRIend") == true
     *      containsTagIgnoreCase("friends", "e/FRIENd") == false
     *       </pre>
     *
     * @param scheduleSet cannot be null
     * @param searchWord  cannot be null, cannot be empty, must be at least a a single word
     */
    public static boolean containsScheduleIgnoreCase(Collection<Schedule> scheduleSet, String searchWord) {
        keywordsPredicateCheckForNull(scheduleSet, searchWord);

        String preppedSchedule = searchWord.trim();
        checkArgument(!preppedSchedule.isEmpty(), "Schedule parameter cannot be empty");
        checkArgument(preppedSchedule.split("\\s+").length == 1, "Schedule parameter should be a single word");

        for (Schedule schedule : scheduleSet) {
            String[] preppedActivity = schedule.activity.toString().split("\\s+");
            for (String wordsInPreppedActivity : preppedActivity) {
                if (wordsInPreppedActivity.equalsIgnoreCase(preppedSchedule)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check for if sentence or searchWord is null .
     */
    private static void keywordsPredicateCheckForNull(Object sentence, String searchWord) {
        requireNonNull(sentence);
        requireNonNull(searchWord);
    }


}
