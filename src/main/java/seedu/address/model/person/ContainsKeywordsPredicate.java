package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author AngularJiaSheng
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name}, Phone, Address, Email, Tag, WebLink,
 * matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (containsKeyWordInName(person) || containsKeyWordInAddress(person)
                || containsKeyWordInPhone(person) || containsKeyWordInTag(person)
                || containsKeyWordInWebLink(person) || containsKeyWordInEmail(person));
    }

    private boolean containsKeyWordInName(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    private boolean containsKeyWordInPhone(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    private boolean containsKeyWordInAddress(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword
            -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
    }

    private boolean containsKeyWordInTag(ReadOnlyPerson person) {
        return person.getTags().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringFilter(), keyword)));
    }

    private boolean containsKeyWordInWebLink(ReadOnlyPerson person) {
        return person.getWebLinks().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toStringWebLink(), keyword)));
    }

    private boolean containsKeyWordInEmail(ReadOnlyPerson person) {
        return person.getEmail().stream().anyMatch(s -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(s.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}
