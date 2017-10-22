package seedu.address.model.module.predicates;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.module.ReadOnlyLesson;


/**
 * Tests that if a {@code ReadOnlyLesson} if in the favourite list.
 */
public class FavouriteListPredicate implements Predicate<ReadOnlyLesson> {

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.isMarked();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteListPredicate); // instanceof handles nulls
    }

}
