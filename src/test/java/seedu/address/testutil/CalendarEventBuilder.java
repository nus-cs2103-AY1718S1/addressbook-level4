package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.EventEndDate;
import seedu.address.model.calendarevent.EventEndTime;
import seedu.address.model.calendarevent.EventName;
import seedu.address.model.calendarevent.EventStartDate;
import seedu.address.model.calendarevent.EventStartTime;
import seedu.address.model.calendarevent.ReadOnlyCalendarEvent;

//@@author yilun-zhu
/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_NAME = "Halloween";
    public static final String DEFAULT_START_DATE = "2017-10-30";
    public static final String DEFAULT_START_TIME = "16:00";
    public static final String DEFAULT_END_DATE = "2017-10-30";
    public static final String DEFAULT_END_TIME = "22:00";

    private CalendarEvent event;

    public CalendarEventBuilder() {
        EventName defaultName = null;
        try {
            defaultName = new EventName(DEFAULT_NAME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's name are invalid.");
        }
        EventStartDate defaultStartDate = null;
        try {
            defaultStartDate = new EventStartDate(DEFAULT_START_DATE);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's start date are invalid.");
        }
        EventStartTime defaultStartTime = null;
        try {
            defaultStartTime = new EventStartTime(DEFAULT_START_TIME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's start time are invalid.");
        }
        EventEndDate defaultEndDate = null;
        try {
            defaultEndDate = new EventEndDate(DEFAULT_END_DATE);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's end date are invalid.");
        }
        EventEndTime defaultEndTime = null;
        try {
            defaultEndTime = new EventEndTime(DEFAULT_END_TIME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's end time are invalid.");
        }
        this.event = new CalendarEvent(defaultName, defaultStartDate, defaultStartTime, defaultEndDate, defaultEndTime);
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code eventToCopy}.
     */
    public CalendarEventBuilder(ReadOnlyCalendarEvent eventToCopy) {
        this.event = new CalendarEvent(eventToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withName(String name) {
        try {
            this.event.setName(new EventName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventStartDate} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withStartDate(String date) {
        try {
            this.event.setStartDate(new EventStartDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventEndDate} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEndDate(String date) {
        try {
            this.event.setEndDate(new EventEndDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventStartTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withStartTime(String time) {
        try {
            this.event.setStartTime(new EventStartTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventEndTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEndTime(String time) {
        try {
            this.event.setEndTime(new EventEndTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    public CalendarEvent build() {
        return this.event;
    }

}
