package seedu.address.model.module;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.Set;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyLesson {

    ObjectProperty<Code> codeProperty();
    Code getCode();
    ObjectProperty<Location> locationProperty();
    Location getLocation();
    ObjectProperty<TimeSlot> timeSlotProperty();
    TimeSlot getTimeSlot();
    ObjectProperty<ClassType> classTypeProperty();
    ClassType getClassType();
    ObjectProperty<Group> groupProperty();
    Group getGroup();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyLesson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getCode().equals(this.getCode()) // state checks here onwards
                && other.getLocation().equals(this.getLocation())
                && other.getTimeSlot().equals(this.getTimeSlot())
                && other.getClassType().equals(this.getClassType())
                && other.getGroup().equals(this.getGroup()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode())
                .append(" Class Type: ")
                .append(getClassType())
                .append(" Location: ")
                .append(getLocation())
                .append(" Group: ")
                .append(getGroup())
                .append(" Time Slot: ")
                .append(getTimeSlot())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
