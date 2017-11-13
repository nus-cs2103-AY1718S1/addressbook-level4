package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author k-l-a
/**
 * Tests that a Person's Tag List contains at least one tag whose string matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public TagListContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_TAG.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getTags().stream().anyMatch(tag -> testTag(tag));
    }

    /**
     * Returns true if a particular tag's string matches any of the keywords. For use of the test method above
     */
    private boolean testTag(Tag tag) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordPartialIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagListContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for tag
    }
}
