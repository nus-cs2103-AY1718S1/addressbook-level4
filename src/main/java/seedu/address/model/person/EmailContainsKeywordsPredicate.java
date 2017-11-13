package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;

import java.util.Comparator;
import java.util.List;

import seedu.address.commons.util.StringUtil;

//@@author nadhira15
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_EMAIL.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for email
    }
}
