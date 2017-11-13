package seedu.address.model.calendarevent;

import javafx.beans.property.ObjectProperty;

//@@author yilun-zhu
/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyCalendarEvent {

    ObjectProperty<EventName> nameProperty();
    EventName getEventName();
    ObjectProperty<EventStartDate> startDateProperty();
    EventStartDate getStartDate();
    ObjectProperty<EventStartTime> startTimeProperty();
    EventStartTime getStartTime();
    ObjectProperty<EventEndDate> endDateProperty();
    EventEndDate getEndDate();
    ObjectProperty<EventEndTime> endTimeProperty();
    EventEndTime getEndTime();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyCalendarEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEventName().equals(this.getEventName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Start: ")
                .append(getStartDate())
                .append(" Time: ")
                .append(getEndTime())
                .append(" End: ")
                .append(getEndDate())
                .append(" Time: ")
                .append(getEndTime());
        return builder.toString();
    }

}
