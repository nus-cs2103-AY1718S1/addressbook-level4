package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

//@@author Alim95

/**
 * Tests that a {@code ReadOnlyPerson}'s details matches any of the keywords given.
 */
public class PersonHasKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private final String fullWord;
    private final boolean isFindPinned;

    public PersonHasKeywordsPredicate(List<String> keywords, boolean isFindPinned) {
        this.keywords = keywords;
        this.isFindPinned = isFindPinned;
        StringJoiner joiner = new StringJoiner(" ");
        for (String key : keywords) {
            joiner.add(key);
        }
        this.fullWord = joiner.toString().toLowerCase();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String[] nameParts = person.getName().fullName.split(" ");
        ArrayList<String> tagParts = getTags(person);
        return !person.isPrivate() && isPersonMatch(person, nameParts, tagParts);
    }

    /**
     * Returns true if the person has properties that matches the keywords.
     */
    private boolean isPersonMatch(ReadOnlyPerson person, String[] nameParts, ArrayList<String> tagParts) {
        for (String tag : tagParts) {
            if (keywords.stream().anyMatch(keyword -> tag.startsWith(keyword.toLowerCase()))) {
                return !isFindPinned || person.isPinned();
            }
        }
        for (String name : nameParts) {
            if (keywords.stream().anyMatch(keyword -> name.toLowerCase().startsWith(keyword.toLowerCase()))) {
                return !isFindPinned || person.isPinned();
            }
        }
        if (keywords.size() != 0 && person.getAddress().toString().toLowerCase().contains(fullWord)) {
            return !isFindPinned || person.isPinned();
        }
        if (keywords.stream().anyMatch(keyword -> person.getEmail().toString().toLowerCase()
                .startsWith(keyword.toLowerCase()))) {
            return !isFindPinned || person.isPinned();
        }
        return keywords.stream().anyMatch(keyword -> person.getPhone()
                .toString().startsWith(keyword.toLowerCase())) && (!isFindPinned || person.isPinned());
    }

    private ArrayList<String> getTags(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        ArrayList<String> tagParts = new ArrayList<>();
        for (Tag tag : tagList) {
            tagParts.add(tag.toTextString().toLowerCase());
        }
        return tagParts;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonHasKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonHasKeywordsPredicate) other).keywords)); // state check
    }

}
