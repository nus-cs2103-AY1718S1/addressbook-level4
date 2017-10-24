package seedu.address.model.module.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

/**
 * An abstract parent class for predicate which name include "ContainsKeywordPredicate"
 */
public abstract class FindPredicate implements Predicate<ReadOnlyLesson> {

    public boolean test(ReadOnlyLesson lesson) {
        return true;
    }

    public abstract List<String> getKeywords();
}
