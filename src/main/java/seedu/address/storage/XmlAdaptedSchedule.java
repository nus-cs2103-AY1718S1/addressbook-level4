package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlValue
    private String schedule;

    /**
     * Constructs an XmlAdaptedSchedule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}

    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSchedule(Schedule source) {
        ScheduleDate scheduleDate = source.scheduleDate;
        Activity activity = source.activity;
        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        // extract out schedule date and activity from schedule string
        String[] tokens = schedule.split(" ");
        String scheduleDate = tokens[1];

        StringBuilder activity = new StringBuilder();
        for (int i = 3; i < tokens.length; i++) {
            activity.append(tokens[i] + " ");
        }

        return new Schedule(new ScheduleDate(scheduleDate), new Activity(activity.toString().trim()));
    }
}
