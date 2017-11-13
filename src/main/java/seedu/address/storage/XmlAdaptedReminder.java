package seedu.address.storage;

import java.time.format.DateTimeParseException;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.Time;

//@@author justinpoh
/**
 * JAXB-friendly version of Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String reminder;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReadOnlyReminder source) {
        this.reminder = source.getReminder();
        this.date = source.getDate().value;
        this.time = source.getTime().value;
    }
    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws DateTimeParseException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        final Date date = new Date(this.date);
        final Time time = new Time(this.time);
        return new Reminder(this.reminder, date, time);
    }


}
