package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

//@@author liliwei25
/**
 * Test Birthday class
 */
public class BirthdayTest {

    private static final String INVALID_YEAR = "12-12-0000";
    private static final String INVALID_MONTH = "12-24-1995";
    private static final String INVALID_DAY = "50-12-1995";
    private static final String INVALID_FORMAT = "1";
    private static final String INVALID_DATE = "a";
    private static final String VALID_DATE = "01-01-2001";
    private static final String VALID_LEAP_YEAR = "29-02-2016";
    private static final String VALID_NON_LEAP_YEAR = "28-02-2017";
    private static final int ONE_DAY = 1;

    @Test
    public void isValidBirthday() {
        // invalid birthday
        assertFalse(Birthday.isValidBirthday(INVALID_DATE)); // non-integer
        assertFalse(Birthday.isValidBirthday(INVALID_FORMAT)); // incorrect format
        assertFalse(Birthday.isValidBirthday(INVALID_DAY)); // incorrect day
        assertFalse(Birthday.isValidBirthday(INVALID_MONTH)); // incorrect month
        assertFalse(Birthday.isValidBirthday(INVALID_YEAR)); // incorrect year

        // valid birthday
        assertTrue(Birthday.isValidBirthday(VALID_DATE)); // valid date
        assertTrue(Birthday.isValidBirthday(VALID_LEAP_YEAR)); // test valid leap year
        assertTrue(Birthday.isValidBirthday(VALID_NON_LEAP_YEAR)); // test valid non-leap year
    }

    @Test
    public void isDateCorrect() {

        // dates after current date
        assertFalse(Birthday.isDateCorrect(LocalDate.now().plusDays(ONE_DAY)));

        // current date
        assertTrue(Birthday.isDateCorrect(LocalDate.now()));

        // before current date
        assertTrue(Birthday.isDateCorrect(LocalDate.now().minusDays(ONE_DAY)));
    }
}
