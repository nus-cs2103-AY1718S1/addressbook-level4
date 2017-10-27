package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleName;


/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String scheduleName;
    @XmlElement
    private List<XmlAdaptedSchedule> schedules = new ArrayList<>();
    /**
     * Constructs an XmlAdapteddSchedule.
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
        for (ReadOnlySchedule schedule: source.getSchedules()) {
            schedules.add(new XmlAdaptedSchedule(schedule));
        }

    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        final ScheduleName scheduleName = new ScheduleName(this.scheduleName);
        return new Schedule(scheduleName);
    }

}
