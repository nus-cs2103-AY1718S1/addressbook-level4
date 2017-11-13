package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} roughly matches any of the keywords given or
 * that any of the keywords are an exact match to any {@code Tag} in {@code UniqueTagList}
 */
public class PersonDataContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonDataContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.isSearchKeyWordsMatchAnyData(keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonDataContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonDataContainsKeywordsPredicate) other).keywords)); // state check
    }

}
