package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;

//@@author nadhira15
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_ADDRESS.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for address
    }
}
