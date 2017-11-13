package seedu.address.storage.elements;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.reminder.ReadOnlyReminder;

//@@author junyango
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {
    @XmlElement(required = true)
    private String message;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {
    }


    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReadOnlyReminder source) {
        message = source.getMessage();
    }
    public String getReminderMessage() {
        return this.message;
    }
}
