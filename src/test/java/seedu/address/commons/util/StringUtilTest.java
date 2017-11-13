package seedu.address.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    //@@author KhorSL
    //---------------- Tests for containsWordIgnoreCaseAndCharacters --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCaseAndCharacters_nullWord_throwsNullPointerException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(NullPointerException.class, "typical sentence",
                null, Optional.empty());
    }

    private void assertExceptionThrownForIgnoreCaseAndCharacters(Class<? extends Throwable> exceptionClass,
                                                        String sentence, String word, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCaseAndCharacters(sentence, word);
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCaseAndCharacters_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForIgnoreCaseAndCharacters(NullPointerException.class, null, "abc", Optional.empty());
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
    public void containsWordIgnoreCaseAndCharacters_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "bb"));
        assertFalse(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "bbbb"));

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc", "Aaa")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa. bBb ccc", "Aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc1", "CCc1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bBb ccc_1", "CCc_1")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("Aaa", "aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters(",Aaa", "aaa")); // boundary case
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("AAA bBb ccc  bbb", "bbB"));
        assertTrue(StringUtil.containsWordIgnoreCaseAndCharacters("AAA b_Bb ccc  bb_b", "bbB"));
    }

    //---------------- Tests for containsDate --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsDate_nullWord_throwsNullPointerException() {
        assertExceptionThrownForDate(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownForDate(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsDate(sentence, word);
    }

    @Test
    public void containsDate_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForDate(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsDate_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForDate(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsDate_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForDate(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for date:
     *   - any date with valid format
     *   - date with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one date
     *   - multiple words with date
     *   - sentence with extra spaces
     *   - sentence with wrong format date
     *
     * Possible scenarios returning true:
     *   - matches first date in sentence
     *   - last date in sentence
     *   - middle date in sentence
     *   - matches multiple dates
     *
     * Possible scenarios returning false:
     *   - query date matches part of a sentence date
     *   - sentence date matches part of the query date
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsDate_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsDate("", "20/10/2017")); // Boundary case
        assertFalse(StringUtil.containsDate("    ", "20/10/2017"));

        // Matches a partial date only
        assertFalse(StringUtil.containsDate("20/10/2017 05:30", "20/10/2018"));
        assertFalse(StringUtil.containsDate("20/10/17 05:30", "20/10/2017")); // Query word bigger than sentence word

        // Sentence with wrong format date
        assertFalse(StringUtil.containsDate("20/10/17", "20/10/17"));

        // Matches word in the date sentence
        assertTrue(StringUtil.containsDate("20/10/2017 bBb ccc", "20/10/2017")); // First word (boundary case)
        assertTrue(StringUtil.containsDate("aaa bBb 20/10/2017", "20/10/2017")); // Last word (boundary case)
        assertTrue(StringUtil.containsDate("  AAA   20/10/2017   ccc  ", "20/10/2017")); // Sentence has extra spaces
        assertTrue(StringUtil.containsDate("20/10/2017", "20/10/2017")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsDate("aaa bbb 20/10/2017", "  20/10/2017  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsDate("AAA 20/10/2017 ccc  20/10/2017", "20/10/2017"));
    }

    //---------------- Tests for containsTime --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsTime_nullWord_throwsNullPointerException() {
        assertExceptionThrownForTime(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownForTime(Class<? extends Throwable> exceptionClass, String sentence, String word,
                                              Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsDate(sentence, word);
    }

    @Test
    public void containsTime_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownForTime(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsTime_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownForTime(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsTime_nullSentence_throwsNullPointerException() {
        assertExceptionThrownForTime(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for time:
     *   - any time with valid format
     *   - time with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one time
     *   - multiple words with time
     *   - sentence with extra spaces
     *   - sentence with wrong format time
     *
     * Possible scenarios returning true:
     *   - matches first time in sentence
     *   - last time in sentence
     *   - middle time in sentence
     *   - matches multiple times
     *
     * Possible scenarios returning false:
     *   - query date matches part of a sentence time
     *   - sentence date matches part of the query time
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsTime_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsTime("", "10:50")); // Boundary case
        assertFalse(StringUtil.containsTime("    ", "10:50"));

        // Matches a partial time only
        assertFalse(StringUtil.containsTime("20/10/2017 05:30", "05:40"));

        // Sentence with wrong format time
        assertFalse(StringUtil.containsTime("5:30", "5:30"));

        // Matches word in the date sentence
        assertTrue(StringUtil.containsTime("05:30 bBb ccc", "05:30")); // First word (boundary case)
        assertTrue(StringUtil.containsTime("aaa bBb 05:30", "05:30")); // Last word (boundary case)
        assertTrue(StringUtil.containsTime("  AAA   05:30   ccc  ", "05:30")); // Sentence has extra spaces
        assertTrue(StringUtil.containsTime("05:30", "05:30")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsTime("aaa bbb 05:30", "  05:30  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsTime("AAA 05:30 ccc  05:30", "05:30"));
    }

    //---------------- Tests for extractDates --------------------------------------

    /*
     * Equivalence Partitions:
     *   - empty sentence
     *   - multiple dates
     *   - single dates
     *   - sentence containing other words other that date
     *
     * Possible scenario returning dates:
     *    - There is date is the first word of the sentence
     *    - date is the middle word of the sentence
     *    - date is the last word of the sentence
     *    - matches multiple date in the sentence
     *    - matches multiple date in a sentence containing other words
     *
     * Possible scenario returning empty ArrayList<String>:
     *     - sentence do not contain any dates
     *     - sentence do not contain date with correct format
     */

    @Test
    public void extractDates_validInputs_correctResults() {
        ArrayList<String> expectedSingleDateList = new ArrayList<>();
        expectedSingleDateList.add("20/10/2017");

        ArrayList<String> expectedMultipleDatesList1 = new ArrayList<>();
        expectedMultipleDatesList1.add("20/10/2017");
        expectedMultipleDatesList1.add("20/10/2018");
        expectedMultipleDatesList1.add("20/10/2019");

        ArrayList<String> expectedMultipleDatesList2 = new ArrayList<>();
        expectedMultipleDatesList2.add("20/10/2017");
        expectedMultipleDatesList2.add("20/10/2017");

        // Empty sentence
        assertCorrectDateResult("", new ArrayList<>());
        assertCorrectDateResult("    ", new ArrayList<>());

        // Sentence with wrong format date
        assertCorrectDateResult("20/10/17", new ArrayList<>());
        assertCorrectDateResult("01/13/2017", new ArrayList<>()); // date wrong format with mm/dd/yyyy
        assertCorrectDateResult("01-01-2017", new ArrayList<>()); // date dd-mm-yyyy

        // single date
        assertCorrectDateResult("20/10/2017", expectedSingleDateList);

        // multiple dates, dates as the first, middle and last word
        assertCorrectDateResult("20/10/2017 20/10/2018 20/10/2019", expectedMultipleDatesList1);
        assertCorrectDateResult("20/10/2017 20/10/2017", expectedMultipleDatesList2); // multiple same dates
        assertCorrectDateResult("20/10/2017 10:50", expectedSingleDateList);
    }

    /**
     * Assert true if {@code sentence} contains dates in {@code expected}
     *
     * @param sentence should not be null
     * @param expected should not be null
     */
    public void assertCorrectDateResult(String sentence, ArrayList<String> expected) {
        ArrayList<String> actual = StringUtil.extractDates(sentence);
        assertTrue(actual.equals(expected));
    }

    //---------------- Tests for extractTimes --------------------------------------

    /*
     * Equivalence Partitions:
     *   - empty sentence
     *   - multiple times
     *   - single times
     *   - sentence containing other words other that time
     *
     * Possible scenario returning times:
     *    - There is time is the first word of the sentence
     *    - date is the middle time of the sentence
     *    - date is the last time of the sentence
     *    - matches multiple time in the sentence
     *    - matches multiple time in a sentence containing other words
     *
     * Possible scenario returning empty ArrayList<String>:
     *     - sentence do not contain any times
     *     - sentence do not contain time with correct format
     */

    @Test
    public void extractTimes_validInputs_correctResults() {
        ArrayList<String> expectedSingleTimeList = new ArrayList<>();
        expectedSingleTimeList.add("05:30");

        ArrayList<String> expectedMultipleTimesList1 = new ArrayList<>();
        expectedMultipleTimesList1.add("00:00");
        expectedMultipleTimesList1.add("10:40");
        expectedMultipleTimesList1.add("23:59");

        ArrayList<String> expectedMultipleTimesList2 = new ArrayList<>();
        expectedMultipleTimesList2.add("10:30");
        expectedMultipleTimesList2.add("10:30");

        // Empty sentence
        assertCorrectTimeResult("", new ArrayList<>());
        assertCorrectTimeResult("    ", new ArrayList<>());

        // Sentence with wrong format time
        assertCorrectTimeResult("0:00", new ArrayList<>());
        assertCorrectTimeResult("5:30", new ArrayList<>());
        assertCorrectTimeResult("24:59", new ArrayList<>());
        assertCorrectTimeResult("05-30", new ArrayList<>());

        // single date
        assertCorrectTimeResult("05:30", expectedSingleTimeList);

        // multiple dates, dates as the first, middle and last word
        assertCorrectTimeResult("00:00 10:40 23:59", expectedMultipleTimesList1);
        assertCorrectTimeResult("10:30 10:30", expectedMultipleTimesList2); // multiple same dates
        assertCorrectTimeResult("20/10/2017 05:30", expectedSingleTimeList);
    }

    /**
     * Assert true if {@code sentence} contains times in {@code expected}
     *
     * @param sentence should not be null
     * @param expected should not be null
     */
    public void assertCorrectTimeResult(String sentence, ArrayList<String> expected) {
        ArrayList<String> actual = StringUtil.extractTimes(sentence);
        assertTrue(actual.equals(expected));
    }
    //@@author

    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
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
