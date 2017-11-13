package seedu.address.model.tag;

//@author Minghui94
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {

        return keywords.stream().anyMatch(keyword ->
        StringUtil.containsTagIgnoreCase(readOnlyPerson.getTags(), keyword)); //getTags() is a set of Tag
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagContainsKeywordsPredicate
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords));
    }
}
