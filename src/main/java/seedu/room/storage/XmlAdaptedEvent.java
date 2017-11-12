package seedu.room.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.event.Datetime;
import seedu.room.model.event.Description;
import seedu.room.model.event.Event;
import seedu.room.model.event.Location;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.Title;


//@@author sushinoya
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String datetime;


    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    public XmlAdaptedEvent(ReadOnlyEvent source) {
        title = source.getTitle().value;
        description = source.getDescription().value;
        location = source.getLocation().value;
        datetime = source.getDatetime().value;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final Title title = new Title(this.title);
        final Description description = new Description(this.description);
        final Location location = new Location(this.location);
        final Datetime datetime = new Datetime(this.datetime);
        return new Event(title, description, location, datetime);
    }
}
