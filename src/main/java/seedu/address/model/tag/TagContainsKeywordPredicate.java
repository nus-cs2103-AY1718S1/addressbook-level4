package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches the keyword given.
 */
public class TagContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;

    public TagContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        try {
            return person.getTags().contains(new Tag(keyword));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("Tag name is expected to be alphanumeric.");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagContainsKeywordPredicate) other).keyword)); // state check
    }

}
