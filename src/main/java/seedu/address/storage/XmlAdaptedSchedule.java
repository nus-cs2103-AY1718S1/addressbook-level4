package seedu.address.storage;

import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;

//@@author CT15
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
    //@@author 17navasaw
    public XmlAdaptedSchedule(Schedule source) {
        ScheduleDate scheduleDate = source.getScheduleDate();
        Activity activity = source.getActivity();
        Set<Name> personInvolvedNames = source.getPersonInvolvedNames();

        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString()
                + " Person(s): " + personInvolvedName.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        // extract out schedule date, activity and person involved from schedule string
        int startingIndexOfDate = 6;
        int endingIndexOfDate = 16;
        int startingIndexOfActivity = schedule.indexOf("Activity: ") + 10;
        int personHeaderIndex = schedule.indexOf("Person(s): ");
        int startingIndexOfPerson = personHeaderIndex + 8;

        String scheduleDate = schedule.substring(startingIndexOfDate, endingIndexOfDate);
        String activity = schedule.substring(startingIndexOfActivity, personHeaderIndex - 1);
        String personInvolvedNames = schedule.substring(startingIndexOfPerson);

        logger.info("Date: " + scheduleDate + " Activity: " + activity + " Person: " + personInvolvedName);
        return new Schedule(new ScheduleDate(scheduleDate), new Activity(activity), new Name(personInvolvedName));
    }
}
