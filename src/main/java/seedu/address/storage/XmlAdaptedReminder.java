package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;

/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String message;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}


    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReadOnlyReminder source) {
        time = source.getTime().getValue();
        message = source.getMessage();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Reminder's object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Reminder toModelType() throws IllegalValueException, PropertyNotFoundException {
        final DateTime time = new DateTime(this.time);
        final String message = this.message;
        return new Reminder(time, message);
    }
}
