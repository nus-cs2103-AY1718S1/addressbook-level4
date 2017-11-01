package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;

/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String message;


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
        name = source.getEvent().getName().toString();
        message = source.getMessage();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Reminder toModelType() throws IllegalValueException, PropertyNotFoundException {
        final String name = this.name;
        final String message = this.message;
        return new Reminder(name, message);
    }
}
