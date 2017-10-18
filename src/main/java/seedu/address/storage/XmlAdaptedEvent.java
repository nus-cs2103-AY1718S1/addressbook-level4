package seedu.address.storage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String name;
    private String desc;
    @XmlElement(required = true)
    private String time;

    @XmlElement
    private List<XmlAdaptedPerson> participants = new ArrayList<>();

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
        name = source.getEName().value;
        desc = source.getDesc().value;
        time = source.getETime().value;
        participants = new ArrayList<>();
        for (Person participant : source.getParticipants()) {
            participants.add(new XmlAdaptedPerson(participant));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> eventParticipants = new ArrayList<>();
        for (XmlAdaptedPerson participant : participants) {
            eventParticipants.add(participant.toModelType());
        }
        final Name name = new Name(this.name);
        final Desc desc = new Desc(this.desc);
        final Time time = new Time(this.time);
        final Set<Person> participants = new HashSet<>(eventParticipants);
        return new Event(name, desc, time, participants);
    }
}
