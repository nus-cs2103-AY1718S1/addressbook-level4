package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author kengying
/**
 * Tests that any of {@code ReadOnlyPerson}'s Name, Phone, Email or Address matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString()
                        .replace("[", "").replace("]", "")
                        .replace(",", ""), keyword)
                        || StringUtil.containsWordIgnoreCase(person.getGroup().groupName, keyword)));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }
    //@@author

}
