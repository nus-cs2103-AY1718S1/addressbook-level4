package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Birthday.EMPTY_BIRTHDAY_FIELD_DAY;
import static seedu.address.model.person.Birthday.EMPTY_BIRTHDAY_FIELD_MONTH;
import static seedu.address.model.person.Birthday.getBirthdayTestInstance;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author justinpoh
public class BirthdayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getMonthOfBirthday() throws Exception {
        // non-empty birthday string
        Birthday birthday1 = new Birthday("21/11/2017");
        assertEquals(11, birthday1.getMonthOfBirthday());

        // empty birthday string
        Birthday emptyBirthday = new Birthday("");
        assertEquals(EMPTY_BIRTHDAY_FIELD_MONTH, emptyBirthday.getMonthOfBirthday());
    }

    @Test
    public void getDayOfBirthday() throws Exception {
        // non-empty birthday string
        Birthday birthday1 = new Birthday("21/11/2017");
        assertEquals(21, birthday1.getDayOfBirthday());

        // empty birthday string
        Birthday emptyBirthday = new Birthday("");
        assertEquals(EMPTY_BIRTHDAY_FIELD_DAY, emptyBirthday.getDayOfBirthday());
    }

    @Test
    public void isBirthdayToday() {
        LocalDate testCurrentDate = LocalDate.of(2017, 12, 10);

        // is birthday today -> returns true
        Birthday birthdayTodaySameYear = getBirthdayTestInstance("10/12/2017", testCurrentDate);
        assertTrue(birthdayTodaySameYear.isBirthdayToday());
        Birthday birthdayTodayDifferentYear = getBirthdayTestInstance("10/12/1995", testCurrentDate);
        assertTrue(birthdayTodayDifferentYear.isBirthdayToday());

        // is not birthday today -> returns true
        Birthday notBirthdayTodayPast = getBirthdayTestInstance("11/12/2017", testCurrentDate);
        assertFalse(notBirthdayTodayPast.isBirthdayToday());

        Birthday notBirthdayTodayUpcoming = getBirthdayTestInstance("13/12/2017", testCurrentDate);
        assertFalse(notBirthdayTodayUpcoming.isBirthdayToday());

    }

    @Test
    public void isBirthdayTomorrow() {
        LocalDate testCurrentDate = LocalDate.of(2017, 12, 10);

        // is birthday tomorrow -> returns true
        Birthday birthdayTomorrowSameYear = getBirthdayTestInstance("11/12/2017", testCurrentDate);
        assertTrue(birthdayTomorrowSameYear.isBirthdayTomorrow());
        Birthday birthdayTomorrowDifferentYear = getBirthdayTestInstance("11/12/2000", testCurrentDate);
        assertTrue(birthdayTomorrowDifferentYear.isBirthdayTomorrow());

        // is not birthday tomorrow -> returns false
        Birthday birthdayToday = getBirthdayTestInstance("10/12/2017", testCurrentDate);
        assertFalse(birthdayToday.isBirthdayTomorrow());

        Birthday birthdayAfterTomorrow = getBirthdayTestInstance("12/12/2017", testCurrentDate);
        assertFalse(birthdayAfterTomorrow.isBirthdayTomorrow());

    }

    @Test
    public void isValidBirthday() {
        // day out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("32.3.1995"));
        assertFalse(Birthday.isValidBirthday("31.4.1995"));
        assertFalse(Birthday.isValidBirthday("0.1.1995"));

        // month out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("21.13.1995"));
        assertFalse(Birthday.isValidBirthday("21.0.1995"));

        // year too small -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.999"));
        assertFalse(Birthday.isValidBirthday("01.01.1899"));

        // year too big -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.10000"));
        assertFalse(Birthday.isValidBirthday("01.01.2100"));

        // input format not correct -> returns false
        assertFalse(Birthday.isValidBirthday("1 January 1999"));
        assertFalse(Birthday.isValidBirthday("10/ 10 / 1992"));

        // input values are not integers -> returns false
        assertFalse(Birthday.isValidBirthday("3.0/10.0/1995"));

        // not even a date -> returns false
        assertFalse(Birthday.isValidBirthday("Not a date"));

        // wrong leap day date -> returns false
        assertFalse(Birthday.isValidBirthday("29/2/2017"));

        // space -> returns true
        assertTrue(Birthday.isValidBirthday(" "));

        // empty string -> returns true
        assertTrue(Birthday.isValidBirthday(""));

        // valid dates using / separator -> returns true
        assertTrue(Birthday.isValidBirthday("31/3/1995"));
        assertTrue(Birthday.isValidBirthday("31/03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03/1995"));

        // valid dates using - separator -> returns true
        assertTrue(Birthday.isValidBirthday("01-03-1995"));
        assertTrue(Birthday.isValidBirthday("31-03-1995"));
        assertTrue(Birthday.isValidBirthday("01-03-1995"));

        // valid dates using . separator -> returns true
        assertTrue(Birthday.isValidBirthday("01.03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03.1995"));
        assertTrue(Birthday.isValidBirthday("01.03.1995"));

        // valid leap year day -> returns true
        assertTrue(Birthday.isValidBirthday("29-2-2016"));
        assertTrue(Birthday.isValidBirthday("29-2-2000"));

        // flexible day-month-year separator -> returns true
        assertTrue(Birthday.isValidBirthday("01/03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03-1995"));
    }

}
