package seedu.address.storage;

import static seedu.address.logic.parser.ScheduleCommandParser.DATE_FORMAT;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.schedule.Schedule;

//@@author limcel
/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dateString;

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
        name = source.getPersonName().toString();
        dateString = DATE_FORMAT.format(source.getDate());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        final String name = this.name;
        final Schedule schedule;
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        if (calendar != null) {
            try {
                calendar.setTime(DATE_FORMAT.parse(dateString));
                return new Schedule(name.toString(), calendar);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        schedule = new Schedule(name.toString(), calendar);
        return new Schedule(name.toString(), calendar);
    }
}
