//@@author aggarwalRuchir
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;


/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<Tag> keywords;

    public PersonContainsKeywordsPredicate(List<Tag> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

    /**
     * Returns all the tags as a single string
     */
    public String returnListOfTagsAsString() {
        String stringOfTags = "";
        for (Tag t : this.keywords) {
            stringOfTags += t.toString() + " ";
        }
        return stringOfTags;
    }


}


