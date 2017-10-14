package seedu.address.model.module;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.Lecturer.Lecturer;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyLesson {

    ObjectProperty<Location> locationProperty();
    Location getLocation();
    ObjectProperty<TimeSlot> timeSlotProperty();
    TimeSlot getTimeSlot();
    ObjectProperty<ClassType> classTypeProperty();
    ClassType getClassType();
    ObjectProperty<Group> groupProperty();
    Group getGroup();
    ObjectProperty<Code> codeProperty();
    Code getCode();
    ObjectProperty<Lecturer> lecturerProperty();
    Lecturer getLecturer();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyLesson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getLocation().equals(this.getLocation()) // state checks here onwards
                && other.getTimeSlot().equals(this.getTimeSlot())
                && other.getClassType().equals(this.getClassType())
                && other.getGroup().equals(this.getGroup())
                && other.getCode().equals(this.getCode())
                && other.getLecturer().equals(this.getLecturer()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Module Code: ")
                .append(getCode())
                .append(" Class Type: ")
                .append(getClassType())
                .append(" Location: ")
                .append(getLocation())
                .append(" Group: ")
                .append(getGroup())
                .append(" Time Slot: ")
                .append(getTimeSlot())
                .append(" Lecturer: ")
                .append(getLecturer());
        return builder.toString();
    }

}
