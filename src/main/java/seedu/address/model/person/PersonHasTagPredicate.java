//@@author WangJieee
package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code UniqueTagList} contains the specific tag.
 */
public class PersonHasTagPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tags;

    public PersonHasTagPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagSet = person.getTags();
        for (Tag t: tagSet) {
            if (tags.stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(t.tagName, tag))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonHasTagPredicate // instanceof handles nulls
                && this.tags.equals(((PersonHasTagPredicate) other).tags)); // state check
    }
}
