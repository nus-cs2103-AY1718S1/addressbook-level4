package seedu.address.model.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;

import seedu.address.commons.util.PartialSearchUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List baseTagList = Arrays.asList(person.getTags().toArray());
        List baseList = new ArrayList();
        ListIterator iter = baseTagList.listIterator();
        while (iter.hasNext()) {
            baseList.add(iter.next().toString());
        }
        PartialSearchUtil mySearch = new PartialSearchUtil(keywords, baseList);
        return mySearch.compare();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
