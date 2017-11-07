package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 */
public class TagsContainKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @param setTags
     * @return
     */
    private String stringifySetTags(Set<Tag> setTags) {
        StringBuilder tagsString = new StringBuilder();
        final String stop = " ";

        Iterator<Tag> tagIterator = setTags.iterator();
        while (tagIterator.hasNext()) {
            Tag checkingTag = tagIterator.next();
            tagsString.append(checkingTag.tagName);
            tagsString.append(stop);
        }

        return tagsString.toString();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringifySetTags(person.getTags()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check
    }
}
