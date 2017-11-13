package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Address} or {@code Email} or {@code Phone}
 * contains any or all of the keywords given, depending on the main keyword.
 * {@code Name}, {@code Email} and {@code Phone} can have any matches. {@code Address} must have all matches.
 */
public class ContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private final String mainKeyword;

    public ContainsKeywordsPredicate(List<String> keywords, String mainKeyword) {
        this.keywords = keywords;
        this.mainKeyword = mainKeyword;
    }

    //@@author Gideonfu
    @Override
    public boolean test(ReadOnlyPerson person) {
        switch (mainKeyword) {
        case FindCommand.KEYWORD_NAME: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        case FindCommand.KEYWORD_ADDRESS: return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));

        case FindCommand.KEYWORD_EMAIL: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));

        case FindCommand.KEYWORD_PHONE: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));

        case FindCommand.KEYWORD_BIRTHDAY: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBirthday().getMonth(), keyword));

        case FindCommand.KEYWORD_TAG: return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString(), keyword));

        case FindCommand.KEYWORD_ORGANISATION: return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getOrganisation().value, keyword));

        default : return false;
        }

    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}
