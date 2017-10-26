package seedu.address.storage;

import java.time.Duration;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;

/**
 * JAXB-friendly adapted version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String eventName;

    @XmlElement(required = true)
    private String eventTime;

    @XmlElement(required = true)
    private String eventDuration;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {

        eventName = source.getEventName().fullName;
        eventTime = DateTimeUtil.parseLocalDateTimeToString(source.getEventTime().getStart());
        eventDuration = String.valueOf(source.getEventDuration().getDuration().toMinutes());

    }


    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Event toModelType() throws IllegalValueException {

        final EventName newName = new EventName(eventName);
        final EventTime newTime = new EventTime(DateTimeUtil.parseStringToLocalDateTime(eventTime),
                Duration.ofMinutes(Long.parseLong(eventDuration)));
        final EventDuration newDuration = new EventDuration(Duration.ofMinutes(Long.parseLong(eventDuration)));

        return new Event(new MemberList(), newName, newTime, newDuration);
    }

}
