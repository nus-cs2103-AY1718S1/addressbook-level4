package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 * TODO: currently, NameContainsKeywordsPredicate will search under address, name and phone. It will be expanded.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //@@Jiasheng
    @Override
    public boolean test(ReadOnlyPerson person) {
        if(containsKeyWordInName(person) || containsKeyWordInAddress(person)
                || containsKeyWordInPhone(person) || containsKeyWordInTag(person)
                || containsKeyWordInWebLink(person)){
            return true;
        }else{
            return false;
        }
    }
    //@@author jiasheng
    private boolean containsKeyWordInName(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }
    //@@author jiasheng
    private boolean containsKeyWordInPhone(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }
    //@@author jiasheng
    private boolean containsKeyWordInAddress(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    //@@author jiasheng
    private boolean containsKeyWordInTag(ReadOnlyPerson person) {
        return person.getTags().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringFilter(), keyword)));
    }

    //@@author jiasheng
    private boolean containsKeyWordInWebLink(ReadOnlyPerson person) {
        return person.getWebLinks().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringWebLink(), keyword)));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
