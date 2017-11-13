package seedu.address.model.meeting;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Melvin-leo
/**
 * A read-only immutable interface for a Meeting in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMeeting {
    ObjectProperty<NameMeeting> nameProperty();
    NameMeeting getName();
    ObjectProperty<DateTime> dateProperty();
    DateTime getDate();
    ObjectProperty<Place> placeProperty();
    Place getPlace();
    ObjectProperty<List<ReadOnlyPerson>> personsMeetProperty();
    List<ReadOnlyPerson> getPersonsMeet();
    ObjectProperty<MeetingTag> meetTagProperty();
    MeetingTag getMeetTag();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMeeting other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getPlace().equals(this.getPlace()));
    }

    /**
     * Formats the meeting as text, showing all meeting details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nMeeting with: ")
                .append(getPersonsMeet().get(0).getName())
                .append("\nContact Number: ")
                .append(getPersonsMeet().get(0).getPhone())
                .append("\nDate and Time: ")
                .append(getDate())
                .append("\nLocation: ")
                .append(getPlace());
        return builder.toString();
    }

    /**
     * Formats the meeting as text, showing all meeting details.
     */
    default String getGroupText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nMeeting with: Group")
                .append("\nDate and Time: ")
                .append(getDate())
                .append("\nLocation: ")
                .append(getPlace());
        return builder.toString();
    }
}
