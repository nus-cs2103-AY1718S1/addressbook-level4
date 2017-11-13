//@@author Hailinx
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Conducts a fuzzy test on whether a {@code ReadOnlyPerson}'s attribute matches any of the keywords given.
 */
public class FuzzySearchPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public FuzzySearchPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(person.getPhone())
                .append(person.getEmail())
                .append(person.getAddress());
        person.getTags().forEach(builder::append);
        String personToString = builder.toString();
        return personToString.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FuzzySearchPredicate // instanceof handles nulls
                && this.keyword.equals(((FuzzySearchPredicate) other).keyword)); // state check
    }
}
