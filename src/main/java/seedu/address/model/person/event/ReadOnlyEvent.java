package seedu.address.model.person.event;

import javafx.beans.property.ObjectProperty;

//@@author royceljh
/**
 * A read-only immutable interface for a Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    ObjectProperty<Header> headerProperty();
    Header getHeader();
    ObjectProperty<Desc> descProperty();
    Desc getDesc();
    ObjectProperty<EventDate> eventDateProperty();
    EventDate getEventDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getHeader().equals(this.getHeader())  // state checks here onwards
                && other.getDesc().equals(this.getDesc())
                && other.getEventDate().equals(this.getEventDate()));
    }

    /**
     * Formats the event as text, showing all details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDesc())
                .append(" Header: ")
                .append(getHeader())
                .append(" Desc: ")
                .append(getDesc())
                .append(" Date: ")
                .append(getEventDate());
        return builder.toString();
    }

}
