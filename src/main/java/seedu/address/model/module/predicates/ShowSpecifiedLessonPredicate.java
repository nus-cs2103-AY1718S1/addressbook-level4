package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

//@@author junming403
/**
 * Tests that a {@code ReadOnlyLesson} matches the given lesson.
 */
public class ShowSpecifiedLessonPredicate implements Predicate<ReadOnlyLesson> {
    private final ReadOnlyLesson lesson;

    public ShowSpecifiedLessonPredicate(ReadOnlyLesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return this.lesson.isSameStateAs(lesson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSpecifiedLessonPredicate // instanceof handles nulls
                && this.lesson.isSameStateAs((((ShowSpecifiedLessonPredicate) other).lesson))); // state check
    }

}
