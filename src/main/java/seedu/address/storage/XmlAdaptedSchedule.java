package seedu.address.storage;

import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlValue
    private String schedule;

    private Logger logger = LogsCenter.getLogger(XmlAdaptedSchedule.class);
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
        ScheduleDate scheduleDate = source.getScheduleDate();
        Activity activity = source.getActivity();
        Name personInvolvedName = source.getPersonInvolvedName();

        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString()
                + " Person: " + personInvolvedName.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        // extract out schedule date and activity from schedule string
        /*String[] tokens = schedule.split(" ");
        String scheduleDate = tokens[1];
        String activity = tokens[3];
        String personInvolvedName = tokens[5];
        for (int i = 6; i < tokens.length; i++) {
            personInvolvedName += " ";
            personInvolvedName += tokens[i];
        }*/
        int personHeaderIndex = schedule.indexOf("Person: ");

        String scheduleDate = schedule.substring(6, 16);
        String activity = schedule.substring(schedule.indexOf("Activity: ") + 10, personHeaderIndex - 1);
        String personInvolvedName = schedule.substring(personHeaderIndex + 8);

        logger.info("Date: " + scheduleDate + " Activity: " + activity + " Person: " + personInvolvedName);
        return new Schedule(new ScheduleDate(scheduleDate), new Activity(activity), new Name(personInvolvedName));
    }
}
