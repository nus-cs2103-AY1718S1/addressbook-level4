package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    public static final String MESSAGE_PHONE_VALIDATION =
            "Phone numbers can only contain numbers, and should be at 4 or 8 digits long";

    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhoneIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
