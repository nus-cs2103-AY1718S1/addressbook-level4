package seedu.address.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void containsNameIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    @Test
    public void containsEmailIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }


    /**
     * assertExceptionThrown
     */
    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsNameIgnoreCase(sentence, word);
        StringUtil.containsEmailIgnoreCase(sentence, word);
    }

    @Test
    public void containsNameIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsEmailIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsNameIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }


    @Test
    public void containsNameIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    @Test
    public void containsEmailIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "gmail.com", Optional.empty());
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

        // Empty sentence
        assertFalse(StringUtil.containsEmailIgnoreCase("", "google.com")); // Boundary case
        assertFalse(StringUtil.containsEmailIgnoreCase("    ", "yahoo.com"));


        //Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsEmailIgnoreCase("abc@example.com", "Example.com")); // Case insensitive
        assertTrue(StringUtil.containsEmailIgnoreCase("abc@example.com", "ExaMPLE.com")); // Case insensitive
        assertTrue(StringUtil.containsEmailIgnoreCase("abc@example.com", "EXAMPLE.COM")); // Case insensitive


        //Matches email with multiple domain
        assertTrue(StringUtil.containsEmailIgnoreCase("abc@example.com.sg", "example.com.sg"));
        assertFalse(StringUtil.containsEmailIgnoreCase("abc@example.com", "example.com.sg"));

        //Matches email of numeric domain
        assertTrue(StringUtil.containsEmailIgnoreCase("abc@123.com", "123.com")); // number email domain

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
