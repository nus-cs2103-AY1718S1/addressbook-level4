package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import com.google.common.base.Joiner;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s details matches any of the keywords given.
 */
public class AnyContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AnyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String stringifyTags = Joiner.on(" ").join(person.getTags());

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                        || StringUtil.containsWordIgnoreCase(stringifyTags, keyword)
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnyContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AnyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
