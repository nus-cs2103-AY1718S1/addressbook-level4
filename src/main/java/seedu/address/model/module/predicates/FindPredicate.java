package seedu.address.model.module.predicates;

import seedu.address.model.module.ReadOnlyLesson;

import java.util.List;
import java.util.function.Predicate;

public abstract class FindPredicate implements Predicate<ReadOnlyLesson> {

    public boolean test(ReadOnlyLesson lesson){ return true; }

    public abstract List<String> getKeywords();
}
