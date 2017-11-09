package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class AnyParticularContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public AnyParticularContainsKeywordsPredicate(List<String> keywords) {
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
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getAddress().toString(), keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getBirthday().toString(), keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getEmail().toString(), keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getPhone().toString(), keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (person.getRemark().getRemarkText(), keyword))
                || keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnyParticularContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AnyParticularContainsKeywordsPredicate) other).keywords)); // state check
    }

}
