package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> personTags = person.getTags();
        String tempAllTagNames = "";
        for (Tag tag: personTags) {
            tempAllTagNames = tempAllTagNames + tag.getTagName() + " ";
        }
        final String allTagNames = tempAllTagNames;

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
