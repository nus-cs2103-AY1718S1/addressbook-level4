package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Group} matches any of the keywords given.
 */

public class GroupContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private String groupFilter;

    public GroupContainsKeywordsPredicate (String group) {
        this.groupFilter = group;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        return readOnlyPerson.getGroup().getGroupName().equals(groupFilter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupContainsKeywordsPredicate // instanceof handles nulls
                && this.groupFilter.equals(((GroupContainsKeywordsPredicate) other).groupFilter)); // state check
    }
}
