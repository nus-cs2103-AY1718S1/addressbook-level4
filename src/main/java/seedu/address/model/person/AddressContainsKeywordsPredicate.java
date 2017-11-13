package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.PartialSearchUtil;

//@@author jianglingshuo
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List baseList = Arrays.asList(person.getAddress().value.split(" "));
        PartialSearchUtil mySearch = new PartialSearchUtil(keywords, baseList);
        return mySearch.compare();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
