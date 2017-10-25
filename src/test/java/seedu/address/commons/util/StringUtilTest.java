package seedu.address.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;
import seedu.address.model.tag.Tag;


public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0"));  // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


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
        StringUtil.containsNameIgnoreCase(sentence, word);
        StringUtil.containsPhoneIgnoreCase(sentence, word);
        StringUtil.containsAddressIgnoreCase(sentence, word);
    }

    private void assertExceptionThrownEmail(Class<? extends Throwable> exceptionClass, Set<Email> emailSet, String word,
                                          Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsEmailIgnoreCase(emailSet, word);
    }

    private void assertExceptionThrownTag(Class<? extends Throwable> exceptionClass, Set<Tag> tagSet, String word,
                                          Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsTagIgnoreCase(tagSet, word);
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
        assertFalse(StringUtil.containsNameIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsNameIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsNameIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsNameIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsNameIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsNameIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsNameIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsNameIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsNameIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsNameIgnoreCase("AAA bBb ccc  bbb", "bbB"));
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
        assertTrue(StringUtil.containsEmailIgnoreCase(emailSet, "ExaMPLE")); // Case insensitive

        //Matches email of numeric domain
        assertTrue(StringUtil.containsEmailIgnoreCase(emailSet, "123")); // number email domain

        //Match email with multiple domain
        assertTrue(StringUtil.containsEmailIgnoreCase(emailSet, "example")); // number email domain

        //Match for non exact word
        assertFalse(StringUtil.containsEmailIgnoreCase(emailSet, "example.com")); //email end with .com domain
        assertFalse(StringUtil.containsEmailIgnoreCase(emailSet, "exam")); // Match partial word

        //Match for email with no '@"
        assertFalse(StringUtil.containsEmailIgnoreCase(emailSet, "gmail")); //email without @
    }


    @Test
    public void containsAddressIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsAddressIgnoreCase("", "abc")); // Boundary case

        //Matches any address field
        assertTrue(StringUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "123"));
        assertTrue(StringUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "Lorong"));
        assertTrue(StringUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "#08-111"));
        assertTrue(StringUtil.containsAddressIgnoreCase("123, Lorong Ave 6, #08-111", "6"));

        //Case insensitive
        assertTrue(StringUtil.containsAddressIgnoreCase("123, Bishan Ave 6, #08-111", "biSHan")); //case insensitive

        //Non exact word match
        assertFalse(StringUtil.containsAddressIgnoreCase("123, Jurong Ave 6, #08-111", "Juron")); //case insensitive
        assertFalse(StringUtil.containsAddressIgnoreCase("123, Jurong West Ave 6, #08-111", "100")); //case insensitive
    }


    @Test
    public void containsPhoneIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsPhoneIgnoreCase("", "98989898")); // Boundary case

        //Matches phone number that has strictly 4 or 8 digits
        assertTrue(StringUtil.containsPhoneIgnoreCase("98984554", "4554"));
        assertTrue(StringUtil.containsPhoneIgnoreCase("98984554", "9898"));
        assertFalse(StringUtil.containsPhoneIgnoreCase("98984554", "455"));
        assertFalse(StringUtil.containsPhoneIgnoreCase("98989898", "98989898")); //search word must be 4 digits

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
        assertTrue(StringUtil.containsTagIgnoreCase(tagSet, "friends"));
        assertTrue(StringUtil.containsTagIgnoreCase(tagSet, "neighbours"));

        //case insensitive
        assertTrue(StringUtil.containsTagIgnoreCase(tagSet, "frIENds"));
        assertTrue(StringUtil.containsTagIgnoreCase(tagSet, "neIGHBOurs"));

        //Non exact word match
        assertFalse(StringUtil.containsTagIgnoreCase(tagSet, "frIENd"));
        assertFalse(StringUtil.containsTagIgnoreCase(tagSet, "BOurs"));

    }


    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getDetails(null);
    }


}
