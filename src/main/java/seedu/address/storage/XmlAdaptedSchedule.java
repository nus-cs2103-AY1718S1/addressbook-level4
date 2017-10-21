package seedu.address.storage;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {
    @XmlAttribute
    private String scheduleDate;
    @XmlAttribute
    private String activity;
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
        scheduleDate = source.scheduleDate.toString();
        activity = source.activity.toString();
        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        return new Schedule(new ScheduleDate(scheduleDate), new Activity(activity));
    }
}
