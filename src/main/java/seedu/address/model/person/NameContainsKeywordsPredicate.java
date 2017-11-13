package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //@@author sidhmads
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean validNames =  keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        if (validNames) {
            return validNames;
        }
        boolean validTags = false;
        for (Tag tag: person.getTags()) {
            validTags = keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
            if (validTags) {
                break;
            }
        }
        return validTags;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
