package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;
import seedu.address.model.tag.Tag;


public class PredicateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    //----------- Tests for containsNameIgnoreCase and containsEmailIgnoreCase --------------
    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    @Test
    public void containsEmailIgnoreCase_nullWord_throwsNullPointerException() {
        Set<Email> emailSet = new HashSet<>();
        try {
            emailSet.add(new Email("dumb@gmail.com"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emailSet should contain emails.");
        }
        assertExceptionThrownEmail(NullPointerException.class, emailSet, null, Optional.empty());
    }

    @Test
    public void containsTagIgnoreCase_nullWord_throwsNullPointerException() {
        Set<Tag> tagSet = new HashSet<Tag>();
        try {
            tagSet.add(new Tag("typicaltags"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagSet should contain tags.");
        }
        assertExceptionThrownTag(NullPointerException.class, tagSet, null, Optional.empty());
    }

    /**
     * assertExceptionThrown
     */
    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsAttributeIgnoreCase(sentence, word);
        PredicateUtil.containsPhoneIgnoreCase(sentence, word);
        PredicateUtil.containsAddressIgnoreCase(sentence, word);
    }

    private void assertExceptionThrownEmail(Class<? extends Throwable> exceptionClass, Set<Email> emailSet, String word,
                                            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsEmailIgnoreCase(emailSet, word);
    }

    private void assertExceptionThrownTag(Class<? extends Throwable> exceptionClass, Set<Tag> tagSet, String word,
                                          Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        PredicateUtil.containsTagIgnoreCase(tagSet, word);
    }

    @Test
    public void containsIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }


    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsNameIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("", "abc")); // Boundary case
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("aaa bbb", "bb")); // Sentence word bigger than query word
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("bbb c", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("  AAA   cc  ", "aaa")); // Sentence has extra spaces
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Aa", "aa")); // Only one word in sentence (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    @Test
    public void containsCountryIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("", "china")); // Boundary case
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("    ", "vietnam"));

        // Matches a partial word only
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("hong kong", "kong"));
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("paris", "par")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Africa", "AFrica")); // case insensitive
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("China London", "LONDOn")); // Last word (boundary case)
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("  Paris  China  ", "China")); // Sentence has extra spaces

        //Full word match
        assertFalse(PredicateUtil.containsAttributeIgnoreCase("Brazi l", " Brazil")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(PredicateUtil.containsAttributeIgnoreCase("Paris Britian", "paris"));
    }

    @Test
    public void containsEmailIgnoreCase_validInputs_correctResult() {
        Set<Email> emailSet = new HashSet<>();
        try {
            emailSet.add(new Email("abc@example.com"));
            emailSet.add(new Email("abc@123.com"));
            emailSet.add(new Email("abc@example.com.sg"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emailSet should contain emails.");
        }

        //Matches word in the sentence, different upper/lower case letters
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "ExaMPLE")); // Case insensitive

        //Matches email of numeric domain
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "123")); // number email domain

        //Match email with multiple domain
        assertTrue(PredicateUtil.containsEmailIgnoreCase(emailSet, "example")); // number email domain

        //Match for non exact word
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "example.com")); //email end with .com domain
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "exam")); // Match partial word

        //Match for email with no '@"
        assertFalse(PredicateUtil.containsEmailIgnoreCase(emailSet, "gmail")); //email without @
    }


    @Test
    public void containsAddressIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsAddressIgnoreCase("", "abc")); // Boundary case

        //Matches any address field
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "123"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "Lorong"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "#08-111"));
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "6"));

        //Case insensitive
        assertTrue(PredicateUtil.containsAddressIgnoreCase("123, Bishan Ave 6, #08-111", "biSHan")); //case insensitive

        //Non exact word match
        assertFalse(PredicateUtil.containsAddressIgnoreCase("12, Jurong Ave 6, #08-11", "Juron")); //case insensitive
        assertFalse(PredicateUtil.containsAddressIgnoreCase("123, Jurong Ave 6, #08-111", "100")); //case insensitive
    }


    @Test
    public void containsPhoneIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("", "98989898")); // Boundary case

        //Matches phone number that has strictly 4 or 8 digits
        assertTrue(PredicateUtil.containsPhoneIgnoreCase("98984554", "4554"));
        assertTrue(PredicateUtil.containsPhoneIgnoreCase("98984554", "9898"));
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("98984554", "455"));
        assertFalse(PredicateUtil.containsPhoneIgnoreCase("98989898", "98989898")); //search word must be 4 digits

    }

    @Test
    public void containsTagsIgnoreCase_validInputs_correctResult() {

        Set<Tag> tagSet = new HashSet<Tag>();
        try {
            tagSet.add(new Tag("friends"));
            tagSet.add(new Tag("neighbours"));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagSet should contain tags.");
        }

        //Exact word match
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "friends"));
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "neighbours"));

        //case insensitive
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "frIENds"));
        assertTrue(PredicateUtil.containsTagIgnoreCase(tagSet, "neIGHBOurs"));

        //Non exact word match
        assertFalse(PredicateUtil.containsTagIgnoreCase(tagSet, "frIENd"));
        assertFalse(PredicateUtil.containsTagIgnoreCase(tagSet, "BOurs"));

    }


}
