package seedu.address.model.event;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Address> addressProperty();
    Address getAddress();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the event as text, showing all event details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Address: ")
                .append(getAddress());
        return builder.toString();
    }

}
