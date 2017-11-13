package seedu.address.model.place;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.tag.Tag;

//@@author huyuanrong
/**
 * Tests that a {@code ReadOnlyPlace}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPlace> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPlace place) {
        Set<Tag> tags = place.getTags();
        List<Tag> tagsList = tags.stream().collect(Collectors.toList());
        return keywords.stream()
                .anyMatch(keyword -> tagsList.stream().anyMatch(tag ->
                        keyword.contains(tag.tagName.toString())) & true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
