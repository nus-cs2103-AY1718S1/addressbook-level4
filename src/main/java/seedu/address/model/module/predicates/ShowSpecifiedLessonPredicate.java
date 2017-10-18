package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;


/**
 * Tests that a {@code ReadOnlyLesson} matches the given lesson.
 */
public class ShowSpecifiedLessonPredicate implements Predicate<ReadOnlyLesson> {
    private final ReadOnlyLesson specifiedLesson;

    public ShowSpecifiedLessonPredicate(ReadOnlyLesson lesson) {
        this.specifiedLesson = lesson;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (lesson.equals(specifiedLesson)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSpecifiedLessonPredicate // instanceof handles nulls
                && this.specifiedLesson.equals(((ShowSpecifiedLessonPredicate) other).specifiedLesson)); // state check
    }

}
