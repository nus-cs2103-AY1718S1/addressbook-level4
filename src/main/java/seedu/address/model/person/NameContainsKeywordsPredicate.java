package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_NAME.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordPartialIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
    //@@author k-l-a
    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return Comparator.comparingInt(person -> StringUtil
                .earliestIndexOf(person.getName().fullName, keywords));
    }

}
