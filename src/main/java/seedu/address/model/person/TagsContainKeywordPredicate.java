package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code tags} matches the keyword given.
 */
public class TagsContainKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagsContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personTagsContainKeyword(person, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagsContainKeywordPredicate) other).keyword)); // state check
    }

    /**
     * Returns true if person's tags list contains keyword (case insensitive)
     */
    private boolean personTagsContainKeyword(ReadOnlyPerson person, String keyword) {
        return person.getTags().stream().anyMatch(tag -> tag.tagName.contains(keyword));
    }
}
