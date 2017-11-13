package seedu.address.model.person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

//@@author k-l-a
/**
 *  Represents a predicate for a field of a ReadOnlyPerson with the ability to test an instance of a ReadOnlyPerson.
 */
public abstract class FieldContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    protected static Comparator<ReadOnlyPerson> defaultSortOrder = null;
    protected List<String> keywords;
    protected String fieldToSearch;


    /**
     * Returns an immutable List of keywords that is used to evaluate a ReadOnlyPerson.
     */
    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    /**
     * Returns the string prefix of the field evaluated by this predicate.
     */
    public String getFieldToSearch() {
        return fieldToSearch;
    }

    /**
     * Creates and returns a comparator based on the keywords and field searched of this predicate.
     */
    public abstract Comparator<ReadOnlyPerson> sortOrderComparator();

}
