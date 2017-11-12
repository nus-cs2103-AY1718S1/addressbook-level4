package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
//@@author willxujun
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Phone} or {@code Tags}
 * matches any of the keywords given.
 */
public class NamePhoneTagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NamePhoneTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /*
    Tests name, primaryPhone, secondary phones in UniquePhoneList and tags sequentially.
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword))
                || keywords.stream()
                    .anyMatch(keyword -> person.getPhones().stream()
                            .anyMatch(phone -> StringUtil.containsWordIgnoreCase(phone.value, keyword)))
                || keywords.stream()
                    .anyMatch(keyword -> person.getTags().stream()
                            .anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NamePhoneTagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NamePhoneTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
