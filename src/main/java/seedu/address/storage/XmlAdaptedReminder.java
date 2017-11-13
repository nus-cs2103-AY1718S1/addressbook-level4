package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
//@@author RonakLakhotia
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String details;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String dueDate;

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
        details = source.getDetails().details;
        priority = source.getPriority().priority;
        dueDate = source.getDueDate().date;
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {

        final ReminderDetails details = new ReminderDetails(this.details);
        final Priority priority = new Priority(this.priority);
        final DueDate dueDate = new DueDate(this.dueDate);

        return new Reminder(details, priority, dueDate);
    }
}

