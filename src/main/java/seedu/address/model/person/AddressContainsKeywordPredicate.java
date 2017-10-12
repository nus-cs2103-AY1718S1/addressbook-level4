package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code address} matches the keyword given.
 */
public class AddressContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public AddressContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getAddress().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((AddressContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
