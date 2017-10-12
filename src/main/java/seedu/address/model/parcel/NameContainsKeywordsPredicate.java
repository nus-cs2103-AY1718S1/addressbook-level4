package seedu.address.model.parcel;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyParcel}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyParcel> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyParcel parcel) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(parcel.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
