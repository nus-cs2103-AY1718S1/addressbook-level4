package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.EventVenue;
import seedu.address.model.event.ReadOnlyEvent;

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
        name = source.getName().eventName;
        time = source.getTime().value;
        venue = source.getVenue().value;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final EventName name = new EventName(this.name);
        final EventTime time = new EventTime(this.time);
        final EventVenue venue = new EventVenue(this.venue);
        return new Event(name, time, venue);
    }
}
