package seedu.address.model.module;

import javafx.beans.property.ObjectProperty;

public interface ReadOnlyModule {
    ObjectProperty<Code> codeProperty();
    Code getCode();
    ObjectProperty<UniqueLessonList> uniqueLessonListProperty();
    UniqueLessonList getUniqueLessonList();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyModule other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getCode().equals(this.getCode())); // state checks here onwards
    }

    /**
     * Formats the Module as text, showing all details info about the module.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode())
                .append(" Lesson List: ")
                .append(getUniqueLessonList());
        return builder.toString();
    }
}
