package seedu.address.storage;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Schedule;

/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedSchedule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}


    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedSchedule(Schedule source) {
        name = source.getPersonName();
        date = Schedule.DATE_FORMAT.format(source.getDate());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        final String name = new Name(this.name).fullName;
        final Schedule schedule;
        Calendar calendar = null;
        // if there is previously a scheduled date, the constructor with scheduled date is called
        if (date != null) {
            calendar = Calendar.getInstance();
            try {
                calendar.setTime(Schedule.DATE_FORMAT.parse(this.date));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            schedule = new Schedule(name, calendar);
        } else {
            schedule = new Schedule(name);
        }
        return new Schedule(name, calendar);
    }
}
