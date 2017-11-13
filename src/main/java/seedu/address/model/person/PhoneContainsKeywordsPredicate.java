package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;

//@@author nadhira15
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_PHONE.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for phone
    }
}
