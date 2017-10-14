package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

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
        Set<Tag> set = person.getTags();
        Tag[] array = set.toArray(new Tag[0]);
        StringJoiner stringjoiner = new StringJoiner(" ");
        for (int i = 0; i < array.length; i++) {
            stringjoiner.add(array[i].toString());
        }
        String finalString = stringjoiner.toString();
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(finalString, '[' + keyword + ']'));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
