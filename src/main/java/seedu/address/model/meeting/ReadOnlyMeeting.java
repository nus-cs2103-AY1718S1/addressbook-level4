package seedu.address.model.meeting;

//import java.util.Date;

import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Meeting in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMeeting {
    ObjectProperty<NameMeeting> nameProperty();
    NameMeeting getName();
    ObjectProperty<DateTime> dateProperty();
    DateTime getDate();
    //ObjectProperty<LocalTime> timeProperty();
    //LocalTime getTime();
    ObjectProperty<Place> placeProperty();
    Place getPlace();
    //ObjectProperty<UniqueTagList> tagProperty();
    //Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMeeting other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                //&& other.getTime().equals(this.getTime())
                && other.getPlace().equals(this.getPlace()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nDate and Time: ")
                .append(getDate())
                //.append(" Time: ")
                //.append(getTime())
                .append("\nLocation: ")
                .append(getPlace());
        return builder.toString();
    }
}
