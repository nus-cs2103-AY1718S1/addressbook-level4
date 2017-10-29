//@@author duyson98

package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches the keyword given.
 */
public class TagContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final Tag tag;

    public TagContainsKeywordPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordPredicate // instanceof handles nulls
                && this.tag.equals(((TagContainsKeywordPredicate) other).tag)); // state check
    }

}
