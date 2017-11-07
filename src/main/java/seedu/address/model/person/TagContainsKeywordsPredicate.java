//@@author erik0704
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (keywords.isEmpty()) {
            return false;
        }
        List<String> tagsAsString = new ArrayList<>();
        for (Tag tag : person.getTags()) {
            tagsAsString.add(tag.tagName);
        }
        return tagsAsString.containsAll(keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}

