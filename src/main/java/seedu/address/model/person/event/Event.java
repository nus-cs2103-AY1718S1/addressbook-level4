package seedu.address.model.person.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//@@author royceljh
/**
 * Represents a Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Header> header;
    private ObjectProperty<Desc> desc;
    private ObjectProperty<EventDate> eventDate;

    /**
     * Every field must be present and not null.
     */
    public Event(Header header, Desc desc, EventDate eventDate) {
        requireAllNonNull(header, desc, eventDate);
        this.header = new SimpleObjectProperty<>(header);
        this.desc = new SimpleObjectProperty<>(desc);
        this.eventDate = new SimpleObjectProperty<>(eventDate);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getHeader(), source.getDesc(), source.getEventDate());
    }

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
    }

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    public void setDesc(Desc desc) {
        this.desc.set(requireNonNull(desc));
    }

    @Override
    public ObjectProperty<Desc> descProperty() {
        return desc;
    }

    @Override
    public Desc getDesc() {
        return desc.get();
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate.set(requireNonNull(eventDate));
    }

    @Override
    public ObjectProperty<EventDate> eventDateProperty() {
        return eventDate;
    }

    @Override
    public EventDate getEventDate() {
        return eventDate.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(header, desc, eventDate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
