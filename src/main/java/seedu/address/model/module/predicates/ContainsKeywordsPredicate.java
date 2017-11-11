package seedu.address.model.module.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

//@@author angtianlannus

/**
 * tests if the given lesson contains the keyword.
 */
public interface ContainsKeywordsPredicate extends Predicate<ReadOnlyLesson> {

    public List<String> getKeywords();

}
