package seedu.address.model.schedule;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

/**
 * A read-only immutable interface for a Group in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlySchedule {

    ObjectProperty<ScheduleName> nameProperty();
    ScheduleName getName();
    ObservableList<ReadOnlySchedule> getSchedules();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlySchedule other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getSchedules().equals(this.getSchedules()));
    }

    /**
     * Formats the Schedule as text, showing schedule name.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Schedule Name: ")
                .append(getName());
        return builder.toString();
    }

}
