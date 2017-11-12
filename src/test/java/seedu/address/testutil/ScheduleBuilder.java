package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.util.SampleDataUtil;

//@@author CT15
/**
 * A utility class to help with building Schedule objects.
 */
public class ScheduleBuilder {
    public static final String DEFAULT_SCHEDULE_DATE = "15-01-1997";
    public static final String DEFAULT_ACTIVITY = "Team Meeting";
    public static final String DEFAULT_PERSON_NAME = "Alex Hunter";

    private Schedule schedule;

    public ScheduleBuilder() {
        try {
            ScheduleDate defaultScheduleDate = new ScheduleDate(DEFAULT_SCHEDULE_DATE);
            Activity defaultActivity = new Activity(DEFAULT_ACTIVITY);
            Set<Name> defaultPersonName = SampleDataUtil.getPersonNamesSet(DEFAULT_PERSON_NAME);
            this.schedule = new Schedule(defaultScheduleDate, defaultActivity, defaultPersonName);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default schedule's values are invalid.");
        }
    }

    /**
     * Initializes the ScheduleBuilder with the data of {@code scheduleToCopy}.
     */
    public ScheduleBuilder(Schedule scheduleToCopy) {
        this.schedule = new Schedule(scheduleToCopy);
    }

    //@@author 17navasaw
    /**
     * Sets the {@code ScheduleDate} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withScheduleDate(String scheduleDate) {
        try {
            schedule = new Schedule(new ScheduleDate(scheduleDate), schedule.getActivity(),
                    schedule.getPersonInvolvedNames());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("schedule date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Activity} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withActivity(String activity) {
        try {
            schedule = new Schedule(schedule.getScheduleDate(), new Activity(activity),
                    schedule.getPersonInvolvedNames());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("activity is expected to be unique.");
        }
        return this;
    }

    //@@author CT15
    /**
     * Sets the {@code personInvolvedNames} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withPersonName(String... personNames) {
        try {
            schedule = new Schedule(schedule.getScheduleDate(), schedule.getActivity(),
                    SampleDataUtil.getPersonNamesSet(personNames));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Person names are expected to be unique.");
        }
        return this;
    }

    public Schedule build() {
        return this.schedule;
    }
}
