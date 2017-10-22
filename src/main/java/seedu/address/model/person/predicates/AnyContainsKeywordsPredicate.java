//@@author A0143832J
package seedu.address.model.person.predicates;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s any detail of <br>
 * {@code Address}, {@code Phone}, {@code Email}, {@code Name} and {@code Tag} <br>
 *  matches any of the keywords given.
 */
public class AnyContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AnyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                || !Collections.disjoint(person.getTagsString(), keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnyContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AnyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
//author
