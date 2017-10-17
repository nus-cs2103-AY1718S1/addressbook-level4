package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Timing;
import seedu.address.model.event.Title;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String timing;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }


    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(ReadOnlyEvent source) {
        title = source.getTitle().toString();
        timing = source.getTiming().toString();
        description = source.getDescription().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Tag> eventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }
        final Title title = new Title(this.title);
        final Timing timing = new Timing(this.timing);
        final Description description = new Description(this.description);
        final Set<Tag> tags = new HashSet<>(eventTags);
        return new Event(title, timing, description, tags);
    }
}

