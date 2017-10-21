package seedu.address.model.event;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.event.timing.Timing;

/**
 * A read-only immutable interface for an Event in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {
    ObjectProperty<Title> titleProperty();

    Title getTitle();

    ObjectProperty<Timing> timingProperty();

    Timing getTiming();

    ObjectProperty<Description> descriptionProperty();

    Description getDescription();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.getTiming().equals(this.getTiming())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the event as text, showing all its details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Timing: ")
                .append(getTiming())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }
}
