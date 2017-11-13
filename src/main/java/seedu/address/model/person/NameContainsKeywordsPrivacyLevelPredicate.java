package seedu.address.model.person;

//@@author jeffreygohkw
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given
 * and has no private fields
 */
public class NameContainsKeywordsPrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPrivacyLevelPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().value, keyword))
                        && !person.hasPrivateField());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPrivacyLevelPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPrivacyLevelPredicate) other).keywords)); //state check
    }

}
