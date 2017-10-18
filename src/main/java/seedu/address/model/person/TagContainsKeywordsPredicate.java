package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tag = new HashSet<>(person.getTags());
        List<String> tagName = new ArrayList<>();
        Iterator<Tag> it = tag.iterator();
        while (it.hasNext()) {
            tagName.add(it.next().tagName);
        }
        String mergedNames = tagName .stream().collect(Collectors.joining(" "));
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(mergedNames, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
