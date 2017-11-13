package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.util.timetable.TimetableParserUtil.MESSAGE_INVALID_DAY;
import static seedu.address.commons.util.timetable.TimetableParserUtil.MESSAGE_INVALID_TIME;
import static seedu.address.commons.util.timetable.TimetableParserUtil.MESSAGE_INVALID_WEEK_TYPE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.person.timetable.TimetableDay;
import seedu.address.model.person.timetable.TimetableInfo;
import seedu.address.model.person.timetable.TimetableSlot;
import seedu.address.model.person.timetable.TimetableWeek;

//@@author zacharytang
public class TimetableTest {

    private static final String INVALID_DAY = "Dayday";
    private static final String INVALID_WEEK_TYPE = "No Week";
    private static final String INVALID_TIME = "080q";

    private static final String VALID_URL_EMPTY = "http://modsn.us/5tN3z";
    private static final String VALID_URL = "http://modsn.us/TDSEt";
    private static final String VALID_WEEK_TYPE = "Odd Week";
    private static final String VALID_DAY = "Monday";
    private static final String VALID_TIME = "0800";
    private static final String VALID_TIME_CHECK = "0900";
    private static final String VALID_DAY_CHECK = "Tuesday";
    private static final String VALID_WEEK_TYPE_CHECK = "Even Week";
    private static final String VALID_WEEK_EVERY = "Every Week";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidUrl() throws Exception {
        // invalid urls
        assertFalse(Timetable.isValidUrl("")); // empty string
        assertFalse(Timetable.isValidUrl(" ")); // spaces only
        assertFalse(Timetable.isValidUrl("abcde12345")); // string
        assertFalse(Timetable.isValidUrl("https://www.youtube.com/")); // other url
        assertFalse(Timetable.isValidUrl("http://modsn.us")); // invalid request
        assertFalse(Timetable.isValidUrl("http://mods.us/TDSEt")); // invalid domain name

        // valid urls
        assertTrue(Timetable.isValidUrl("http://modsn.us/TEt")); // bad short url, but still valid
        assertTrue(Timetable.isValidUrl(VALID_URL)); // valid url
        assertTrue(Timetable.isValidUrl(VALID_URL_EMPTY)); // valid url, empty timetable
    }

    @Test
    public void updateSlot() throws Exception {
        TimetableSlot slotToUpdate = new TimetableSlot();
        slotToUpdate.setLesson();
        assertNotEquals(slotToUpdate, new TimetableSlot());
    }

    @Test
    public void timetableDay() throws Exception {
        TimetableDay dayToUpdate = new TimetableDay();

        assertEquals(dayToUpdate, dayToUpdate);
        assertNotEquals("Hello", dayToUpdate);

        dayToUpdate.updateSlotsWithLesson(VALID_TIME, VALID_TIME_CHECK);

        assertNotEquals(dayToUpdate, new TimetableDay());
        assertTrue(dayToUpdate.doesSlotHaveLesson(VALID_TIME));
        assertFalse(dayToUpdate.doesSlotHaveLesson(VALID_TIME_CHECK));
    }

    @Test
    public void timetableWeek() throws Exception {
        TimetableWeek weekToUpdate = new TimetableWeek();

        assertEquals(weekToUpdate, weekToUpdate);
        assertNotEquals("Hello", weekToUpdate);

        weekToUpdate.updateSlotsWithLesson(VALID_DAY, VALID_TIME, VALID_TIME_CHECK);

        assertNotEquals(weekToUpdate, new TimetableWeek());
        assertTrue(weekToUpdate.doesSlotHaveLesson(VALID_DAY, VALID_TIME));
        assertFalse(weekToUpdate.doesSlotHaveLesson(VALID_DAY_CHECK, VALID_TIME));
        assertFalse(weekToUpdate.doesSlotHaveLesson(VALID_DAY, VALID_TIME_CHECK));
    }

    @Test
    public void timetableInfo() throws Exception {
        TimetableInfo timetableToUpdate = new TimetableInfo();

        assertEquals(timetableToUpdate, timetableToUpdate);
        assertNotEquals("Hello", timetableToUpdate);

        timetableToUpdate.updateSlotsWithLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME, VALID_TIME_CHECK);
        assertNotEquals(timetableToUpdate, new TimetableInfo());
        assertTrue(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE_CHECK, VALID_DAY, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY_CHECK, VALID_TIME));
        assertFalse(timetableToUpdate.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME_CHECK));
    }

    @Test
    public void timetableQueries_invalidWeekType_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_WEEK_TYPE);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(INVALID_WEEK_TYPE, VALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_validEveryWeek_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage("Please specify a week type!");
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_EVERY, VALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_invalidDay_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_DAY);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, INVALID_DAY, VALID_TIME);
    }

    @Test
    public void timetableQueries_invalidTime_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_TIME);
        Timetable timetable = new Timetable(VALID_URL_EMPTY);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, INVALID_TIME);
    }

    @Test
    public void timetableQueries_validQuery_success() throws Exception {
        Timetable timetable = new Timetable(VALID_URL);
        timetable.doesSlotHaveLesson(VALID_WEEK_TYPE, VALID_DAY, VALID_TIME);
    }
}
