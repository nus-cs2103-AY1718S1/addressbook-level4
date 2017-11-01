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


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrownSentence(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownSentence(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownSentence(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownSentence(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCase_emptySentence_throwsNullPointerException() {
        assertExceptionThrownSentence(NullPointerException.class, null, "abc", Optional.empty());
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
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //@@author LeeYingZheng
    //---------------- Tests for containsTag ------------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    //overload for taglist, a set of tags
    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, Set<Tag> tagList, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsTag(tagList, word);
    }

    public Set<Tag> setupTestTag() {
        try {

            Set<Tag> testTag = new HashSet<Tag>();

            Tag friend = new Tag("friend");
            Tag colleague = new Tag("colleague");
            Tag neighbour = new Tag("neighbour");
            Tag family = new Tag("family");
            testTag.add(friend);
            testTag.add(colleague);
            testTag.add(neighbour);
            testTag.add(family);

            return testTag;
        } catch (IllegalValueException e) {
            return null;
        }
    }


    @Test
    public void containsTag_nullWord_throwsNullPointerException() {
        Set<Tag> testTag = setupTestTag();
        assertExceptionThrown(NullPointerException.class, testTag, null, Optional.empty());
    }


    @Test
    public void containsTag_emptyWord_throwsIllegalArgumentException() {
        Set<Tag> testTag = setupTestTag();
        assertExceptionThrown(IllegalArgumentException.class, testTag, "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsTag_nullTags_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Possible scenarios returning true:
     *   - matches any tags of a person, regardless of number of spaces behind the query words
     *   - if there are multiple words, any words that match any tags of a person
     *
     * Possible scenarios returning false:
     *   - query word does not follow case of the tag word
     *   - query word matches part of a tag word
     *   - tags match part of the query word
     *   - word(s) does not match any of the tags of a person
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsTag_validInputs_correctResult() {

        Set<Tag> testTag = setupTestTag();
        // Unmatched Tags
        assertFalse(StringUtil.containsTag(testTag, "abc")); // Boundary case
        assertFalse(StringUtil.containsTag(testTag, "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsTag(testTag, "famil")); // Tag word bigger than query word
        assertFalse(StringUtil.containsTag(testTag, "neighbours")); // Query word bigger than tag word

        // Matches word in the tags, different upper/lower case letters
        assertFalse(StringUtil.containsTag(testTag, "Friend")); // incorrect case of query word
        assertTrue(StringUtil.containsTag(testTag, "family")); // correct case of query word
        assertTrue(StringUtil.containsTag(testTag, " colleague  ")); // query word contains spaces at its boundaries

        // Matches multiple words in Tags
        assertTrue(StringUtil.containsTag(testTag , "friend colleague")); // query contains multiple words
        assertTrue(StringUtil.containsTag(testTag , "family tutor")); //query contains a matched and unmatched word
    }
    //@@author

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
