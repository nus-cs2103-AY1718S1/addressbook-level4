package seedu.address.model.module;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Lecturer;
import seedu.address.model.tag.UniqueLecturerList;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Module in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Module implements ReadOnlyModule {

    private ObjectProperty<Code> code;
    private ObjectProperty<UniqueLessonList> lessonList;

    private ObjectProperty<UniqueLecturerList> lecturers;

    /**
     * Every field must be present and not null.
     */
    public Module(Code code, UniqueLessonList lessonList) {
        requireAllNonNull(code, lessonList);
        this.code = new SimpleObjectProperty<>(code);
        this.lessonList = new SimpleObjectProperty<>(lessonList);
    }

    /**
     * Creates a copy of the given ReadOnlyModule.
     */
    public Module(ReadOnlyModule source) {
        this(source.getCode(), source.getUniqueLessonList());
    }

    public void setCode(Code code) {
        this.code.set(requireNonNull(code));
    }

    @Override
    public ObjectProperty<Code> codeProperty() {
        return code;
    }

    @Override
    public Code getCode() { return code.get(); }

    @Override
    public ObjectProperty<UniqueLessonList> uniqueLessonListProperty() { return lessonList; }

    @Override
    public UniqueLessonList getUniqueLessonList() { return lessonList.get(); }

    /**
     * Replaces this module's lesson list with the lesson list in the argument lesson set.
     */
    public void setLessonList(UniqueLessonList replacement) {
        lessonList.set(replacement);
    }

    /**
     * Returns an immutable lecturer set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Lecturer> getLecturers() {
        return Collections.unmodifiableSet(lecturers.get().toSet());
    }

    public ObjectProperty<UniqueLecturerList> lecturerProperty() {
        return lecturers;
    }

    /**
     * Replaces this modules's lecturers with the lecturers in the argument lecturer set.
     */
    public void setLecturers(Set<Lecturer> replacement) { lecturers.set(new UniqueLecturerList(replacement)); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyLesson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyModule) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, getUniqueLessonList());
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
