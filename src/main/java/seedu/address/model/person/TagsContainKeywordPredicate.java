package seedu.address.model.person;

import java.util.Iterator;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

//@@author thehelpfulbees

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class TagsContainKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagsContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        //return false;

        Iterator<Tag> tags = person.getTags().iterator();

        while (tags.hasNext()) {
            if (tags.next().toString().equals("[" + keyword + "]")) {
                return true;
            }
            //else tags.remove();
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagsContainKeywordPredicate) other).keyword)); // state check
    }

}
