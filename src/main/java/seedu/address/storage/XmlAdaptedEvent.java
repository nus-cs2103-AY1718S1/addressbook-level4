package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.person.event.ReadOnlyEvent;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String header;
    @XmlElement(required = true)
    private String desc;
    @XmlElement(required = true)
    private String eventDate;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        header = source.getHeader().value;
        desc = source.getDesc().value;
        eventDate = source.getEventDate().eventLocalDate.toString();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final Header header = new Header(this.header);
        final Desc desc = new Desc(this.desc);
        final EventDate eventDate = new EventDate(this.eventDate);
        return new Event(header, desc, eventDate);
    }
}
