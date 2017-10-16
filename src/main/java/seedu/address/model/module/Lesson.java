package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.lecturer.UniqueLecturerList;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Lesson implements ReadOnlyLesson {
    private ObjectProperty<ClassType> classType;
    private ObjectProperty<Group> group;
    private ObjectProperty<Location> location;
    private ObjectProperty<TimeSlot> timeSlot;
    private ObjectProperty<Code> code;

    private ObjectProperty<UniqueLecturerList> lecturers;

    /**
     * Every field must be present and not null.
     */

    public Lesson(ClassType classType, Location location, Group group, TimeSlot timeSlot, Code code,
                  Set<Lecturer> lecturers) {
        requireAllNonNull(classType, location, group, timeSlot, lecturers);
        this.classType = new SimpleObjectProperty<>(classType);
        this.location = new SimpleObjectProperty<>(location);
        this.group = new SimpleObjectProperty<>(group);
        this.code = new SimpleObjectProperty<>(code);
        this.timeSlot = new SimpleObjectProperty<>(timeSlot);
        // protect internal lecturers from changes in the arg list
        this.lecturers = new SimpleObjectProperty<>(new UniqueLecturerList(lecturers));
    }

    /**
     * Creates a copy of the given ReadOnlyLesson.
     */
    public Lesson(ReadOnlyLesson source) {
        this(source.getClassType(), source.getLocation(), source.getGroup(), source.getTimeSlot(),
                source.getCode(), source.getLecturers());
    }

    public void setLocation(Location location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    @Override
    public Location getLocation() {
        return location.get();
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot.set(requireNonNull(timeSlot));
    }

    @Override
    public ObjectProperty<TimeSlot> timeSlotProperty() {
        return timeSlot;
    }

    @Override
    public TimeSlot getTimeSlot() {
        return timeSlot.get();
    }

    public void setClassType(ClassType classType) {
        this.classType.set(requireNonNull(classType));
    }

    @Override
    public ObjectProperty<ClassType> classTypeProperty() {
        return classType;
    }

    @Override
    public ClassType getClassType() {
        return classType.get();
    }

    public void setGroupType(Group group) {
        this.group.set(requireNonNull(group));
    }

    @Override
    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    @Override
    public Group getGroup() {
        return group.get();
    }

    public void setCodeType(Code code) {
        this.code.set(requireNonNull(code));
    }

    @Override
    public ObjectProperty<Code> codeProperty() {
        return code;
    }

    @Override
    public Code getCode() {
        return code.get();
    }

    @Override
    public Set<Lecturer> getLecturers() {
        return Collections.unmodifiableSet(lecturers.get().toSet());
    }

    @Override
    public ObjectProperty<UniqueLecturerList> lecturersProperty() {
        return lecturers;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Lecturer> replacement) {
        lecturers.set(new UniqueLecturerList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyLesson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyLesson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(classType, location, group, timeSlot, lecturers);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
