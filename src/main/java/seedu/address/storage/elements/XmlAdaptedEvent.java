package seedu.address.storage.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.property.Property;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.Reminder;


//@@author junyango


/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String venue;
    @XmlElement
    private List<XmlAdaptedReminder> reminders = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedProperty> properties = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getName().getValue();
        time = source.getTime().getValue();
        venue = source.getAddress().getValue();
        properties = new ArrayList<>();
        for (Property property: source.getProperties()) {
            properties.add(new XmlAdaptedProperty(property));
        }
        reminders = new ArrayList<>();
        for (Reminder reminder: source.getReminders()) {
            reminders.add(new XmlAdaptedReminder(reminder));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException, PropertyNotFoundException, DuplicatePropertyException {
        final List<Property> eventProperties = new ArrayList<>();
        for (XmlAdaptedProperty property: properties) {
            eventProperties.add(property.toModelType());
        }
        final Set<Property> properties = new HashSet<>(eventProperties);
        final ArrayList<Reminder> eventReminders = new ArrayList<>();
        Event event = new Event(properties, eventReminders);
        for (XmlAdaptedReminder reminder : reminders) {
            event.addReminder(new Reminder(event, reminder.getReminderMessage()));
        }
        return event;
    }
}
