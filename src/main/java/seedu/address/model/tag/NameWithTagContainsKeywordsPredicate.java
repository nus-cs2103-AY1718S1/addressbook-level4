package seedu.address.model.tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

//@@author JYL123
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code tag.getTagName()} matches any of the keywords given.
 */
public class NameWithTagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameWithTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<String> tagList = new HashSet<>();
        for (Tag tag : person.getTags()) {
            tagList.add(tag.getTagName());
        }

        return keywords.stream()
                .anyMatch(tagList::contains);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameWithTagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameWithTagContainsKeywordsPredicate) other).keywords)); // state check
    }
}
