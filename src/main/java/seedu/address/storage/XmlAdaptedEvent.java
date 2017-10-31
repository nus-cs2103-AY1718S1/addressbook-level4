package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;

//@@author leonchowwenhao
/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String eventName;
    @XmlElement
    private String eventDesc;
    @XmlElement(required = true)
    private String eventTime;

    @XmlElement
    private List<XmlAdaptedPersonNoParticipation> participants = new ArrayList<>();

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
        eventName = source.getEventName().fullEventName;
        eventDesc = source.getDescription().eventDesc;
        eventTime = source.getEventTime().eventTime;
        participants = new ArrayList<>();
        for (Person participant : source.getParticipants()) {
            participants.add(new XmlAdaptedPersonNoParticipation(participant));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> eventParticipants = new ArrayList<>();
        for (XmlAdaptedPersonNoParticipation participant : participants) {
            eventParticipants.add(participant.toModelType());
        }
        final EventName eventName = new EventName(this.eventName);
        final EventDescription eventDesc = new EventDescription(this.eventDesc);
        final EventTime eventTime = new EventTime(this.eventTime);
        final Set<Person> participants = new HashSet<>(eventParticipants);
        return new Event(eventName, eventDesc, eventTime, participants);
    }
}
