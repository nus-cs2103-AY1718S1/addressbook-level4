package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;

//@@author yewshengkai
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson>  {
    private final List<String> keywords;
    private final String commandWord;
    private final boolean isCaseIgnored;

    public PersonContainsKeywordsPredicate(String commandWord, List<String> keywords, boolean isCaseIgnored) {
        this.keywords = keywords;
        this.commandWord = commandWord;
        this.isCaseIgnored = isCaseIgnored;
    }
    /**
     * Tests that a {@code ReadOnlyPerson}'s {@code commandWord} matches any of the keywords given.
     * Also check whether if the keyword is case-sensitive or insensitive
     */
    @Override
    public boolean test(ReadOnlyPerson person) {

        switch (commandWord) {

        case FindCommand.COMMAND_WORD:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getName().fullName, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_ADDRESS:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getAddress().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_EMAIL:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getEmail().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_HOMEPAGE:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getHomepage().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_PHONE:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getPhone().value, keyword, isCaseIgnored));
        case FindCommand.COMMAND_WORD_TAG:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            person.getTags().toString()
                            .replaceAll("['\\[\\]']", ""), keyword, isCaseIgnored));

        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
