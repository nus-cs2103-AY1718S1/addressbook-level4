package seedu.address.storage;

//@@author chernghann
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Address;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String address;
    @XmlElement (required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        name = source.getName().fullName;
        date = source.getDate().value;
        address = source.getAddress().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Event toModelType() throws IllegalValueException {
        final EventName name = new EventName(this.name);
        final Date date = new Date(this.date);
        final Address address = new Address(this.address);

        return new Event(name, date, address);
    }
}
