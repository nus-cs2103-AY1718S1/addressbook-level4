package seedu.address.model.reminder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.reminders.Date.DATE_DAY_INDEX;
import static seedu.address.model.reminders.Date.DATE_MONTH_INDEX;
import static seedu.address.model.reminders.Date.DATE_SPLIT_REGEX;
import static seedu.address.model.reminders.Date.DATE_YEAR_INDEX;
import static seedu.address.model.reminders.Status.getStatusTestInstance;
import static seedu.address.model.reminders.Time.HOUR_MIN_SEPARATOR;
import static seedu.address.model.reminders.Time.TIME_HOUR_INDEX;
import static seedu.address.model.reminders.Time.TIME_MIN_INDEX;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.Status;
import seedu.address.testutil.ReminderBuilder;

//@@author justinpoh
public class StatusTest {

    private static final String DEFAULT_DATE = "10/10/2017";
    private static final String DATE_DAY_PAST = "5/10/2017";
    private static final String DATE_MONTH_PAST = "10/9/2017";
    private static final String DATE_YEAR_PAST = "10/10/2016";
    private static final String DATE_DAY_UPCOMING = "15/10/2017";
    private static final String DATE_MONTH_UPCOMING = "10/11/2017";
    private static final String DATE_YEAR_UPCOMING = "10/10/2018";
    private static final String ONE_DAY_AWAY = "11/10/2017";
    private static final String TWO_DAYS_AWAY = "12/10/2017";
    private static final String THREE_DAYS_AWAY = "13/10/2017";
    private static final String FOUR_DAYS_AWAY = "14/10/2017";
    private static final String ONE_DAY_BEFORE = "09/10/2017";

    private static final String DEFAULT_TIME = "12:30";
    private static final String TIME_HOUR_PAST = "09:30";
    private static final String TIME_MINUTE_PAST = "12:10";
    private static final String TIME_HOUR_UPCOMING = "13:30";
    private static final String TIME_MINUTE_UPCOMING = "12:40";

    @Test
    public void hasEventPassed() throws Exception {

        // day past -> returns true
        Reminder reminder1 = new ReminderBuilder().withDate(DATE_DAY_PAST).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status1.hasEventPassed());

        // month past -> returns true
        Reminder reminder2 = new ReminderBuilder().withDate(DATE_MONTH_PAST).withTime(DEFAULT_TIME).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status2.hasEventPassed());

        // year past -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(DATE_YEAR_PAST).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.hasEventPassed());

        // hour past -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.hasEventPassed());

        // minute past -> returns true
        Reminder reminder5 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_MINUTE_PAST).build();
        Status status5 = getStatusTestInstance(reminder5, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status5.hasEventPassed());

        // upcoming day -> returns false
        Reminder reminder6 = new ReminderBuilder().withDate(DATE_DAY_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status6 = getStatusTestInstance(reminder6, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status6.hasEventPassed());

        // upcoming month -> returns false
        Reminder reminder7 = new ReminderBuilder().withDate(DATE_MONTH_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status7 = getStatusTestInstance(reminder7, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status7.hasEventPassed());

        // upcoming year -> returns false
        Reminder reminder8 = new ReminderBuilder().withDate(DATE_YEAR_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status8 = getStatusTestInstance(reminder8, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status8.hasEventPassed());

        // upcoming hour -> returns false
        Reminder reminder9 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_UPCOMING).build();
        Status status9 = getStatusTestInstance(reminder9, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status9.hasEventPassed());

        // upcoming minute -> returns false
        Reminder reminder10 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_MINUTE_UPCOMING).build();
        Status status10 = getStatusTestInstance(reminder10, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status10.hasEventPassed());

        // same date and time -> returns false
        Reminder reminder11 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status11 = getStatusTestInstance(reminder11, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status11.hasEventPassed());
    }

    @Test
    public void isEventToday() {
        // different date -> returns false
        Reminder reminder1 = new ReminderBuilder().withDate(DATE_DAY_PAST).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status1.isEventToday());

        // time past -> returns false
        Reminder reminder2 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status2.isEventToday());

        // same date, same time -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.isEventToday());

        // same date, upcoming time -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_UPCOMING).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.isEventToday());
    }

    @Test
    public void isEventWithinThreeDays() {
        // one day before -> returns false
        Reminder reminder1 = new ReminderBuilder().withDate(ONE_DAY_BEFORE).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status1.isEventWithinThreeDays());

        // same day -> returns true
        Reminder reminder2 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status2.isEventWithinThreeDays());

        // one day later -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(ONE_DAY_AWAY).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.isEventWithinThreeDays());

        // two days later -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(TWO_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.isEventWithinThreeDays());

        // three days later -> returns true
        Reminder reminder5 = new ReminderBuilder().withDate(THREE_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status5 = getStatusTestInstance(reminder5, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status5.isEventWithinThreeDays());

        // four days later -> returns false
        Reminder reminder6 = new ReminderBuilder().withDate(FOUR_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status6 = getStatusTestInstance(reminder6, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status6.isEventWithinThreeDays());

        // same day, time past -> returns false
        Reminder reminder7 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status7 = getStatusTestInstance(reminder7, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status7.isEventWithinThreeDays());
    }

    @Test
    public void equals() throws Exception {

        // Since value of Status.value is dependent on the methods
        // Status.hasEventPassed, Status.isEventToday, Status.isEventWithinThreeDays,
        // this equals test would not test for Status.value so thoroughly as the tests
        // for the methods are already conducted above.

        Reminder reminder1 = new ReminderBuilder().build();
        Status status1 = new Status(reminder1);

        // different type -> returns false
        assertFalse(status1.equals(100));

        // null -> returns false
        assertFalse(status1.equals(null));

        // same object -> returns true
        assertTrue(status1.equals(status1));

        // same value -> returns true
        Reminder reminder2 = new ReminderBuilder(reminder1).build();
        Status status2 = new Status(reminder2);
        assertTrue(status1.equals(status2));
    }

    /**
     * Converts a string in the form dd/mm/yyyy, dd.mm.yyyy or dd-mm-yyyy
     * into a LocalDate object.
     */
    private LocalDate convertStringToLocalDate(String date) {
        String[] splitDate = date.split(DATE_SPLIT_REGEX);

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        return LocalDate.of(year, month, day);
    }

    /**
     * Converts a string in the form hh:mm into a LocalTime object.
     */
    private LocalTime convertStringToLocalTime(String time) {
        String[] splitTime = time.split(HOUR_MIN_SEPARATOR);
        final int hour = Integer.parseInt(splitTime[TIME_HOUR_INDEX]);
        final int min = Integer.parseInt(splitTime[TIME_MIN_INDEX]);
        return LocalTime.of(hour, min);
    }
}
