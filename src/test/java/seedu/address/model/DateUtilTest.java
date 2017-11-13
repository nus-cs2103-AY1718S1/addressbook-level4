package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.util.DateUtil.checkLeapYear;
import static seedu.address.model.util.DateUtil.compareDates;
import static seedu.address.model.util.DateUtil.convertStringToDate;
import static seedu.address.model.util.DateUtil.formatDate;
import static seedu.address.model.util.DateUtil.getMonthFromString;
import static seedu.address.model.util.DateUtil.isValidDateFormat;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import seedu.address.model.util.DateUtil;

//@@author lawwman
public class DateUtilTest {
    private static final String sampleDate1 = "Thu, 18 Oct, Year 2018";
    private static final String sampleDateInput1 = "18-10-2018";
    private static final Date sampleDateClass1 = new GregorianCalendar(2018, 9, 18).getTime();
    private static final Date sampleOldDateClass1 = new GregorianCalendar(2018, 8, 18).getTime();
    private static final String sampleDate2 = "Fri, 13 Dec, Year 2019";
    private static final String sampleDateInput2 = "13-12-2019";
    private static final Date sampleDateClass2 = new GregorianCalendar(2019, 11, 13).getTime();
    private static final Date sampleOldDateClass2 = new GregorianCalendar(2019, 10, 13).getTime();
    private static final String sampleDate3 = "Sat, 05 Jan, Year 2019";
    private static final String sampleDateInput3 = "05-01-2019";
    private static final Date sampleDateClass3 = new GregorianCalendar(2019, 0, 5).getTime();
    private static final Date sampleOldDateClass3 = new GregorianCalendar(2018, 11, 5).getTime();
    private static final String sampleDate4 = "Sat, 23 Mar, Year 2019";
    private static final String sampleDateInput4 = "23-03-2019";
    private static final Date sampleDateClass4 = new GregorianCalendar(2019, 2, 23).getTime();
    private static final Date sampleOldDateClass4 = new GregorianCalendar(2019, 1, 23).getTime();

    @Test
    public void formatDateTest() {
        assertEquals(sampleDate1, formatDate(sampleDateInput1));
        assertEquals(sampleDate1, formatDate(sampleDateClass1));
        assertEquals(sampleDate2, formatDate(sampleDateInput2));
        assertEquals(sampleDate2, formatDate(sampleDateClass2));
        assertEquals(sampleDate3, formatDate(sampleDateInput3));
        assertEquals(sampleDate3, formatDate(sampleDateClass3));
        assertEquals(sampleDate4, formatDate(sampleDateInput4));
        assertEquals(sampleDate4, formatDate(sampleDateClass4));
    }

    @Test
    public void checkLeapYearTest() {
        // leap years have to be divisible by 4
        // if a leap year is divisible by 100, it is not a leap year unless it is ALSO divisible by 400
        assertFalse(checkLeapYear(2017));
        assertFalse(checkLeapYear(2019));
        assertFalse(checkLeapYear(1999));
        assertFalse(checkLeapYear(1995));
        assertFalse(checkLeapYear(1800));
        assertFalse(checkLeapYear(2200));

        assertTrue(checkLeapYear(2000));
        assertTrue(checkLeapYear(2400));
        assertTrue(checkLeapYear(2016));
        assertTrue(checkLeapYear(2020));
    }

    @Test
    public void isValidDateTest() {
        // invalid dates
        assertFalse(isValidDateFormat("3-3-2019")); // correct format is 03-03-2019
        assertFalse(isValidDateFormat("3-march-2019")); // correct format is 03-03-2019
        assertFalse(isValidDateFormat("3rd March 2019")); //correct format is 03-03-2019
        assertFalse(isValidDateFormat("29-02-2017")); // only leap years have 29 days

        // valid dates
        assertTrue(isValidDateFormat(sampleDateInput1));
        assertTrue(isValidDateFormat(sampleDateInput2));
        assertTrue(isValidDateFormat(sampleDateInput3));
        assertTrue(isValidDateFormat(sampleDateInput4));
    }

    @Test
    public void convertStringToDateTest() {
        assertEquals(convertStringToDate(sampleDate1), sampleDateClass1);
        assertEquals(convertStringToDate(sampleDate2), sampleDateClass2);
        assertEquals(convertStringToDate(sampleDate3), sampleDateClass3);
        assertEquals(convertStringToDate(sampleDate4), sampleDateClass4);
    }

    @Test
    public void getMonthFromStringTest() {
        assertEquals(1, getMonthFromString("Jan"));
        assertEquals(2, getMonthFromString("Feb"));
        assertEquals(3, getMonthFromString("Mar"));
        assertEquals(4, getMonthFromString("Apr"));
        assertEquals(5, getMonthFromString("May"));
        assertEquals(6, getMonthFromString("Jun"));
        assertEquals(7, getMonthFromString("Jul"));
        assertEquals(8, getMonthFromString("Aug"));
        assertEquals(9, getMonthFromString("Sep"));
        assertEquals(10, getMonthFromString("Oct"));
        assertEquals(11, getMonthFromString("Nov"));
        assertEquals(12, getMonthFromString("Dec"));
    }

    @Test
    public void compareDateTest() {
        //same date
        assertTrue(compareDates(new Date(), new Date()));
        //first date before second one
        assertTrue(compareDates(sampleDateClass1, sampleDateClass2));
        assertTrue(compareDates(sampleDateClass3, sampleDateClass4));
        //second date before first one
        assertFalse(compareDates(sampleDateClass2, sampleDateClass1));
        assertFalse(compareDates(sampleDateClass4, sampleDateClass3));
    }

    @Test
    public void generateOutdatedDebtDateTest() {
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass1), sampleOldDateClass1);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass2), sampleOldDateClass2);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass3), sampleOldDateClass3);
        assertEquals(DateUtil.generateOutdatedDebtDate(sampleDateClass4), sampleOldDateClass4);

    }

    @Test
    public void getNumberOfMonthBetweenDatesTest() {
        // one month gap
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass1, sampleOldDateClass1), 1);
        // 1 month gap, but both dates have different years.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass2, sampleOldDateClass2), 1);
        // 5 month gap, both dates have different years.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(sampleDateClass4, sampleDateClass1), 5);
        // Large gap between both dates.
        assertEquals(DateUtil.getNumberOfMonthBetweenDates(new GregorianCalendar(2021, 2, 23).getTime(),
                sampleDateClass1), 29);

    }
}
