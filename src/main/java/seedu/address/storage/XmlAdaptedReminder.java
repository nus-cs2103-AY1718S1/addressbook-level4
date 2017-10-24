package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String message;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        taskName = source.getTask().taskName;
        priority = source.getPriority().value;
        date = source.getDate().date;
        message = source.getMessage().message;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        final List<Tag> reminderTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            reminderTags.add(tag.toModelType());
        }
        final Task taskName = new Task(this.taskName);
        final Priority priority = new Priority(this.priority);
        final Date date = new Date(this.date);
        final Message message = new Message(this.message);
        final Set<Tag> tags = new HashSet<>(reminderTags);
        return new Reminder(taskName, priority, date, message, tags);
    }
}
