package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author jelneo
/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class PersonContainsTagPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tagKeywords;

    public PersonContainsTagPredicate(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    /**
     * Evaluates this predicate on the given person.
     *
     * @return {@code true} if the person matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        for (Tag tag : tagList) {
            if (!tagKeywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.tagKeywords.equals(((PersonContainsTagPredicate) other).tagKeywords)); // state check
    }
}
