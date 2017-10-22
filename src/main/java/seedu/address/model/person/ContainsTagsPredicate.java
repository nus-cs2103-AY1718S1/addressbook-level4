package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the tags given.
 */
public class ContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (Tag tag : person.getTags()) {
            for (String keyword : keywords) {
                if (keyword.equalsIgnoreCase(tag.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsTagsPredicate) other).keywords)); // state check
    }

}
