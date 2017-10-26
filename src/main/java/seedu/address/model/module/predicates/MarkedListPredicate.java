package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

/**
 * Tests that if a {@code ReadOnlyLesson} if in the marked list.
 */
public class MarkedListPredicate implements Predicate<ReadOnlyLesson> {

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.isMarked();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkedListPredicate); // instanceof handles nulls
    }

}
