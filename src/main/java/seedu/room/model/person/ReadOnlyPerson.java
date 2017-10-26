package seedu.room.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.room.model.tag.Tag;
import seedu.room.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the residentbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson extends Comparable {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Room> roomProperty();
    Room getRoom();
    ObjectProperty<Picture> pictureProperty();
    Picture getPicture();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Timestamp> timestampProperty();
    Timestamp getTimestamp();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getRoom().equals(this.getRoom()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Room: ")
                .append(getRoom())
                .append(" Time-of-expiry: ")
                .append(getTimestamp().getExpiryTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns the highlight status of the person
     */
    boolean getHighlightStatus();
}
