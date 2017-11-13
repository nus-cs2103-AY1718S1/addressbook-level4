package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.schedule.ScheduleName;

//@@author Procrastinatus
/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String scheduleName;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String scheduleDuration;
    @XmlElement
    private String scheduleDetails;
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
    public XmlAdaptedSchedule(ReadOnlySchedule source) {
        scheduleName = source.getName().toString();
        startDateTime = source.getStartDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
        scheduleDuration = source.getScheduleDuration();
        scheduleDetails = source.getScheduleDetails();

    }

    /**
     * Converts this jaxb-friendly adapted schedule object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        final ScheduleName scheduleName = new ScheduleName(this.scheduleName);
        final ScheduleDate startDateTime = new ScheduleDate(this.startDateTime);
        final ScheduleDate endDateTime = new ScheduleDate(this.endDateTime);
        final String scheduleDuration = this.scheduleDuration;
        final String scheduleDetails = this.scheduleDetails;
        return new Schedule(scheduleName, startDateTime, endDateTime, scheduleDuration, scheduleDetails);
    }

}
