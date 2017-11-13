package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author chuaweiwen
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name, @code Tag} matches all of the keywords given.
 */
public class NameAndTagsContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

    public NameAndTagsContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean hasTag = false;

        int numTagKeywords = tagKeywords.size();
        int tagsMatchedCount = 0;
        if (!tagKeywords.isEmpty()) {
            tagsMatchedCount = countTagMatches(person);
        }

        if (tagsMatchedCount == numTagKeywords) {
            hasTag = true;
        }

        boolean hasName = false;
        if (!nameKeywords.isEmpty()) {
            hasName = nameKeywords.stream().allMatch(nameKeywords -> StringUtil
                    .containsWordIgnoreCase(person.getName().fullName, nameKeywords));
        }

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new AssertionError("Either name or tag must be non-empty");
        } else if (nameKeywords.isEmpty()) {
            return hasTag;
        } else if (tagKeywords.isEmpty()) {
            return hasName;
        }

        return hasName && hasTag;
    }

    /**
     * Counts the number of matching tags of a person and returns the count
     */
    public int countTagMatches(ReadOnlyPerson person) {
        int tagsMatchedCount = 0;

        for (String keywords : tagKeywords) {
            if (containsTag(keywords, person)) {
                tagsMatchedCount++;
            }
        }
        return tagsMatchedCount;
    }

    /**
     * Returns true if the person's tag can be found in the keywords. Otherwise returns false.
     */
    public boolean containsTag(String keywords, ReadOnlyPerson person) {
        Set<Tag> tagsOfPerson = person.getTags();
        for (Tag tag : tagsOfPerson) {
            if (tag.tagName.equalsIgnoreCase(keywords)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndTagsContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .nameKeywords)
                && this.tagKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .tagKeywords)); // state check
    }

}
//@@author
