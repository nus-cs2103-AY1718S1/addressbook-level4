package seedu.address.model.tag;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;
//@@author ZhangH795
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches keyword given.
 * If looseFind is true, the predicate will compare substrings of tagName and keyword
 * Otherwise, the predicate will compare the exact word
 */
public class TagMatchingKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;
    private final boolean looseFind;

    public TagMatchingKeywordPredicate(String keyword, boolean looseFind) {
        this.keyword = keyword;
        this.looseFind = looseFind;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        if (keyword.trim().isEmpty()) {
            return false;
        } else {
            for (Tag tag : tagList) {
                String current = tag.tagName;
                if (current.equals(keyword)) {
                    return true;
                } else if (looseFind && (current.toLowerCase().contains(keyword.toLowerCase())
                        || keyword.toLowerCase().contains(current.toLowerCase()))) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchingKeywordPredicate // instanceof handles nulls
                && this.keyword.equalsIgnoreCase(((TagMatchingKeywordPredicate) other).keyword)); // state check
    }

    public String getKeyword() {
        return keyword;
    }
}
