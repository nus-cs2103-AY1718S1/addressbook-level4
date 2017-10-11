package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.Timing;
import seedu.address.model.event.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Jack's Birthday";
    public static final String DEFAULT_TIMING = "1900-2100";
    public static final String DEFAULT_DESCRIPTION = "Celebrating Jack's 21st, party all night";
    public static final String DEFAULT_TAGS = "birthday";

    private Event event;

    public EventBuilder() {
        try {
            Title defaultTitle = new Title(DEFAULT_TITLE);
            Timing defaultTiming = new Timing(DEFAULT_TIMING);
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.event = new Event(defaultTitle, defaultTiming, defaultDescription, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(ReadOnlyEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code Title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        try {
            this.event.setTitle(new Title(title));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String... tags) {
        try {
            this.event.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Timing} of the {@code Event} that we are building.
     */
    public EventBuilder withTiming(String timing) {
        try {
            this.event.setTiming(new Timing(timing));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("timing is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        try {
            this.event.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
