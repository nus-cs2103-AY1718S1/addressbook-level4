package seedu.address.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches keyword given.
 */
public class TagMatchingKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagMatchingKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        for (Tag tag : tagList) {
            String current = tag.tagName;
            if (current.equalsIgnoreCase(keyword)) {
                return true;
            } else if (keyword.trim().isEmpty()) {
                return false;
            } else if (current.toLowerCase().contains(keyword.toLowerCase())
                || keyword.toLowerCase().contains(current.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchingKeywordPredicate // instanceof handles nulls
                && this.keyword.equalsIgnoreCase(((TagMatchingKeywordPredicate) other).keyword)); // state check
    }

    public String getKeyword() {
        return keyword;
    }
}
