package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

//@@author junming403
/**
 * Tests that a {@code ReadOnlyLesson} matches the given lesson.
 */
public class ShowSpecifiedLessonPredicate implements Predicate<ReadOnlyLesson> {
    private final int hashcode;

    public ShowSpecifiedLessonPredicate(int hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.hashCode() == hashcode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSpecifiedLessonPredicate // instanceof handles nulls
                && this.hashcode == (((ShowSpecifiedLessonPredicate) other).hashcode)); // state check
    }

}
